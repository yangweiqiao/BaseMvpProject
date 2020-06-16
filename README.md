@ (Android kotlin下的多模块化MVP架构)

## 基础项目创建

> 1. 创建项目 App
> 2. 创建lib base 和provider
> 3. 添加依赖关系 App->provider ->base
> 4. base中创建包 common 通用包 data 数据包,其中包括net网络请求 protocol
>    实体类包 ; ext: kotlin扩展方法包
>    presenter包:mvp层基类,包括presenter和view层的包; rx:支持rx相关;
>    ui包:activity和fragment的基类; widgets:ui自定义组件
> 5. provider中创建包: common;
>    event:作为中间实践传递;router:模块之间的相互通信和接口调用;
> 6. App中创建包:common ui

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200615171324346.png)![在这里插入图片描述](https://img-blog.csdnimg.cn/20200615171339805.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lBTkdXRUlRSUFP,size_16,color_FFFFFF,t_70)

依赖关系
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200615171847962.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lBTkdXRUlRSUFP,size_16,color_FFFFFF,t_70)


## 搭建模块化

### 模块化

1. Application和library application是作为应用程序启动 :

```kotlin
apply plugin:'com.android.application' ; 
```

     library是作为库工程被引用:

```kotlin
apply plugin:'com.android.library'
```

2. 方便测试单模块,可以动态的配置

```kotlin
		  if(xxx.toBoolean()){
		   apply plugin:'com.android.application'
		   } else{
		   apply plugin:'com.android.library'
		   }
```

对应两套
AndroidManifest,一套是作为application时候使用,一套是作为lib时候使用;
作为application时候使用,是测试的时候,创建一下放在debug目录下,另外一套放在release下;
在build.gradle中动态的切换


```kotlin
 sourceSets {
        main {
            if (xxx.toBoolean()) {
                manifest.srcFile 'src/main/release/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/debug/AndroidManifest.xml'
            }
        }
    }
```

动态加载模块类型 使用UserCenter测试动态加载模块

### 基础模块封装

~~视图层框架 : butterKnife 在kotlin多模块下的工程中不能使用,报错 unable
to find resource ID 错误原因: module中资源id与主工程资源ID不一致~~
1. android-extensions


> 视图绑定,可直接使用xml中id操作该控件 插件级别,无需引用第三方库
> 无需定义变量,极大减少代码 适用于activity fragment adapter 和自定义view
> apply plugin: 'kotlin-android-extensions' 使用 : 第一步 添加插件
> 第二步: 直接操作xml中的id

2. anko kotlin提供的一些方便操作的库


> Anko Commons 工具库 ,例如 toast intent跳转 Anko layouts 布局 Anko
> SQLLite 数据库 Anko coroutines 协程

```kotlin
 implementation "org.jetbrains.anko:anko:$anko_version"
```

[github](https://github.com/Kotlin/anko)


### 主流框架配置和集成

#### MVP配置

![在这里插入图片描述](https://img-blog.csdnimg.cn/202006141513516.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lBTkdXRUlRSUFP,size_16,color_FFFFFF,t_70)
定义baseView

```kotlin
interface BaseView {

    fun  onShowLoading()
    fun  onHideLoading()
    fun  onError()

}
```

定义 basePresenter ,持有view的引用 ,通过泛型的方式

```kotlin
open class BasePresenter<T : BaseView> {

    lateinit var mView: T
}
```

定义baseMvpActivity来承载p和v ,持有basepresenter的应用 泛型实现
,实现BaseView

```kotlin
open class BaseMvpActivity<T: BasePresenter<*>>:BaseActivity(),BaseView {
    override fun onError() {

    }

    override fun onShowLoading() {

    }

    override fun onHideLoading() {
    }

lateinit var mPresenter:T
}
```

在其他的activity中改造下,测试mvp

```kotlin
class MainActivity : BaseMvpActivity<MinePresenter>() ,MineView {
    override fun result(b: Boolean) {
        toast("toast$b")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MinePresenter()
        mPresenter.mView=this

        mTextView.onClick {

            mPresenter.test()

        }
    }
}
```

对用的p和v

```kotlin
class MinePresenter : BasePresenter<MineView>() {
    fun test() {
        mView.result(true)
    }
}
```

```kotlin
interface MineView:BaseView{
    fun  result(b:Boolean)

}
```

#### RxKotlin 和 RxAndroid 配置

##### RxKotlin

> RxKotlin 基于RxJava的扩展扩,以kotlin的风格提供大量的扩展方法
> 响应式编程 观察者模式 引入依赖包` api
> "io.reactivex:rxkotlin:$rx_koltin" `
> [github](https://github.com/ReactiveX/RxKotlin)

##### RxAndroid

>  RxAndroid 基于rxjava,可以优雅的处理异步请求
>  更好的兼容android的特性,例如主线程,ui事件 引入依赖包` api
>  "io.reactivex:rxandroid:$rx_android"`

测试 1.创建service层

```kotlin
interface UserService {
    fun  register (username:String , password:String ): Observable<Boolean>
}
```

实现 service

```kotlin
class UserServiceImpl : UserService {
    override fun register(username: String, password: String): Observable<Boolean> {
        return Observable.just(true)
    }
}
```

在persenter层调用

```kotlin
 fun register() {

        val userServiceImpl = UserServiceImpl()
        userServiceImpl.register("", "")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Subscriber<Boolean>() {
                override fun onCompleted() {
                }

                override fun onError(e: Throwable?) {
                }

                override fun onNext(t: Boolean?) {
                    mView.onResult(t!!)
                }
            })

    }
```

Subscriber 改造 抽取基类,后续只需要实现这里的onNext方法

```kotlin
class BaseSubscriber<T> : Subscriber<T>() {
    override fun onNext(t: T) {
         
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable?) {
    }
}
```

使用扩展方法 精简 订阅监听

```kotlin
fun <T> Observable<T>.execute(baseSubscriber: BaseSubscriber<T>) {
    this.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(baseSubscriber)
}
```

然后再使用的地方精简代码

```kotlin
    userServiceImpl.register("", "")
            .execute(object :BaseSubscriber<Boolean>(){
                override fun onNext(t: Boolean?) {
                    mView.onResult(t!!)
                }
            })
```

#### [Retrofit](https://square.github.io/retrofit/)集成

##### 集成

```
基于okhttp封装的网络库 ; 
```

集成

```
     api 'com.squareup.okhttp3:okhttp:3.9.0'
     api 'com.squareup.retrofit2:retrofit:2.3.0'
     api 'com.squareup.retrofit2:converter-gson:2.3.0'//retrofit2的Json转换器(默认用Gson)
     api 'com.squareup.okhttp3:logging-interceptor:3.9.0'//okhttp提供的请求日志拦截器
     api 'com.squareup.retrofit2:adapter-rxjava:2.3.0'
```

创建RetrofitFactory ,单例模式

```kotlin
class RetrofitFactory private constructor() {

    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)  //主机地址
            .addConverterFactory(GsonConverterFactory.create()) //数据转换
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(initClient())
            .build()
    }

    //构建client
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(initLogIntercept()) //日志拦截器
            .addInterceptor(initHeaderIntercept()) //日志拦截器
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    //请求头 拦截器
    private fun initHeaderIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", "utf-8")
                .build()
            chain.proceed(request)
        }
    }

    //构建日志拦截器
    private fun initLogIntercept(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor;
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

}
```

使用上面的retrofit进行请求测试

```kotlin
interface ApiService {
    @POST("regist")
    fun regist(@Body user: User): Observable<BaseResp<String>>
}
```

```kotlin
    val user = User("1111", "1111")

        val hello = RetrofitFactory.instance.create(ApiService::class.java).regist(user)

        return hello.flatMap { t ->
            if (t!!.status == 200) {
                Observable.just(true)
            } else {
                Observable.just(false)
            }
        }
```

##### 优化

#### [dagger2](https://www.imooc.com/article/22317)集成

```kotlin
apply plugin: 'kotlin-kapt' //每一个使用的模块都要配置
```

```kotlin
api 'com.google.dagger:dagger-android:2.21'
kapt 'com.google.dagger:dagger-compiler:2.21' //kapt 每个使用的模块都要配置 
```

##### 依赖注入


1. @Inject

> 标记在类的构造 标注实例属性

2. @Component

> 注入器,连接目标类和依赖实例的桥梁 ;
> 以@Component标注的类必须是接口或者是抽象类;
> Component依赖关系通过dependencies属性添加 ;
> APP必须有一个Component来管理全局实例

3. @Module 在不能使用inject直接标记构造函数的时候使用

>  接口不能实例化,只能通过实现类实例化 Module是一个工厂,创建类实例的方法
>  Component通过modules属性添加多个module

4. @Provides

> 在Module 中,使用@Provides标注创建实例的方法 实例化流程
>
> >  Component搜索@Inject注解的属性
> >  Component查找Module中以@Provides注解的对应方法,创建实例

Inject和Module
1. Inject在标注构造函数时候使用
2. Module在不能标记构造函数的时候使用
3. Module的优先级高于Inject构造函数
4. 查找到实例对象,依次查看其参数实例化
5. Module中存在@Provides创建实例的方法,就不用查找inject标注的构造函数,如果再Module中没有找到,就去找Inject构造函数


```kotlin
依赖注入
         Module的优先级高于Inject构造函数
        步骤1：查找Module中是否存在创建该类的方法。就是用 @Provides 标注的providesXXX的方法
        步骤2：若存在创建类方法，查看该方法是否存在参数
            步骤2.1：若存在参数，则按从**步骤1**开始依次初始化每个参数
            步骤2.2：若不存在参数，则直接初始化该类实例，一次依赖注入到此结束
        步骤3：若不存在创建类方法，则查找Inject注解的构造函数，
                   看构造函数是否存在参数
            步骤3.1：若存在参数，则从**步骤1**开始依次初始化每个参数
            步骤3.2：若不存在参数，则直接初始化该类实例，一次依赖注入到此结束

 
```

```kotlin
//使用Inject标注构造函数的类
class ClassA @Inject constructor(){
}

class ClassB{
//在B中可以直接@Inject标注实例属性 
@Inject 
lateinit var mClassA:ClassA

}
//使用Component连接起来A和B 

```

##### 作用域

@Scope

>  用处就是Component的组织
>  更好的管理Component之间的组织方式,不管是依赖方式还是包含方式,都有必要用自定义的Scope注解标注这些Component,这些注解最好不要一样,不一样是为了能更好的体现出
>  component之间的组织方式,还有编辑器检查依赖关系或者是包含关系的Component,若发现有Component没有用自定义的scope注解标注,就会报错
>  更好的管理component与module之间的匹配关系,编译器会检查Component管理的Modules,若发现Component的自定义的Scope注解与Modules中的标注创建类实例方法的注解不一样,就会报错

@Scope和@Singleton


@Scope作用域

>  主要用于component的组织方式  
>  管理Component和Module之间的匹配的关系 提高可读性,见名知意


@Singleton


> 并没有实现单例的能力 是Scope的一种默认实现
> ApplicationComponent单例是由代码控制实现

自定义Scope


> 以Component组织方式自定义Scope


```kotlin
 			 @Scope
           @Retention(RetentionPolicy.RUNTIME)
           annotation class ActivityScope
```

>   没有作用域的component的不能依赖有作用域的component


##### 限定符 @Qualifier 限定符

> 解决依赖注入迷失(同一个接口有多个实现类,编译报错,分不清楚使用哪一个实现类)

@Named

>    Qualifier的一种实现方式 ,以名称来区分使用哪种注解实现

自定义Qualifier

>    @Qualifier @Retention(RetentionPolicy.RUNTIME) annotation class
>    ActivityQualifier


#### [RxLifecycle](https://github.com/trello/RxLifecycle)配置使用

1. 解决Rx内存泄漏
2. 可以监听activity,fragment的生命周期,自动断开Rx绑定
3. 引入


```kotlin
 api 'com.trello:rxlifecycle-components:1.0'
 api 'com.trello:rxlifecycle-kotlin:1.0'
```

可能出现jsr305的问题 : 解决方法： 在build.gradle的 Android{ }里添加：

```kotlin
configurations.all {
      resolutionStrategy.force 'com.google.code.findbugs:jsr305:1.3.9'
  }
```

1. 让BaseActivity继承RxAppCompatActivity

```kotlin
open class BaseActivity:RxAppCompatActivity()
```

2. 采用依赖注入的方式 ,最终的是要注入到Observable的扩展方法中


```kotlin
fun <T> Observable<T>.execute(
    baseSubscriber: BaseSubscriber<T>,
    lifecycleProvider: LifecycleProvider<*>
) {
    this.observeOn(AndroidSchedulers.mainThread())
    //最终要传递到这个地方
        .compose(lifecycleProvider.bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .subscribe(baseSubscriber)
}
```

3. 这个方法是在P层调用的

```kotlin
  serviceImpl.register("", "")
  //调用扩展方法 , 通过参数将 lifecycleProvider传递过去 
            .execute(object : BaseSubscriber<Boolean>() {
                override fun onNext(t: Boolean) {
                    println("shuju fanhui 111111111111111")
                    mView.onResult(t)
                }
            } , lifecycleProvider)
```

4. 在BasePresenter中注入 ,因为所有的presenter都会用到

```kotlin
open class BasePresenter<T : BaseView> {

    lateinit var mView: T
    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>
}
```

5. 下面创建对应的Module,因为是第三方库,不能直接Inject标注进来

```kotlin
@Module
class LifecycleProviderModule(private  val lifecycleProvider: LifecycleProvider<*>) {

    @Provides
    @ActivityScope //使用activity的作用域 
    fun providesLifecycleProvider( ):LifecycleProvider<*>{
        return lifecycleProvider
    }

}
```

6. 在activity的Component中依赖引入

```kotlin
@ActivityScope
@Component(
    modules = arrayOf(ActivityModule::class, LifecycleProviderModule::class), //引入LifecycleProviderModule
    dependencies = arrayOf(AppComponent::class)
)
interface ActivityComponent {

    fun activity(): Activity
    fun context(): Context
    fun lifecycleProvider(): LifecycleProvider<*> //同时暴露方法出去 
}
```

7. 编译下,然后在BaseMvpActivity中修改

```kotlin
 activityComponent = DaggerActivityComponent
            .builder()
            .appComponent((application as BaseApplication).appComponent)
            .activityModule(ActivityModule(this))
            .lifecycleProviderModule(LifecycleProviderModule(this)) //这里添加了一行LifecycleProviderModule的引入,传递this是因为基类继承的是RxAppCompatActivity,它本身是实现了LifecycleProvider的接口,所有直接传递this
            .build()
```

8. 这样就把LifecycleProvider依赖进来了,在BasePresenter那里可以使用@Inject来引入,所有的Presenter都可以直接使用,然后再传递到扩展方法中,这样就绑定完成了

#### 网络请求数据转换优化和扩展

```kotlin
/*
   扩展数据转换
*/
fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}
/*
    扩展Boolean类型数据转换
 */
fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFunBoolean())
}

```

定义上面的BaseFunc

```kotlin
class BaseFunc<T>:Func1<BaseResp<T>,Observable<T>>{
    override fun call(t: BaseResp<T>): Observable<T> {
        //这个地方flatmap转换 ,决定 ,绑定后最终执行哪个error方法还是next方法;
        if (t.status  != 200){
            return Observable.error(BaseException(t.status, t.msg ))
        }

        return Observable.just(t.data)
    }
}
```

定义出 BaseFunBoolean

```kotlin
class BaseFunBoolean<T> : Func1<BaseResp<T>, Observable<Boolean>> {

    override fun call(t: BaseResp<T>): Observable<Boolean> {
        if (t.status == 200) {
            return Observable.just(true)
        }
        return Observable.error(BaseException(t.status, t.msg))
    }
}
```

在使用的地方

```kotlin
//直接转换数据 
repository.regist().convertBoolean()
```


#### [ARouter](https://github.com/alibaba/ARouter) 集成
1. 引入


```kotlin
//ARouter 在baseLib中引入包 
    compile "com.alibaba:arouter-api:$arouter_api_version"
```

在我们的模块中使用,首先要再gradle中配置

```kotlin
android {
    compileSdkVersion 29
    buildToolsVersion "29.0.2"
   ...

    defaultConfig {
 javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
        ...
}
}
dependencies {
   // compile 'com.alibaba:arouter-api:x.x.x' 在base中已经配置 不需要了
   //apt注解框架的声明
    kapt 'com.alibaba:arouter-compiler:$arouter_compiler_version' //注意版本号
    ...
}

```

简单的用法

定义路径

```kotlin
object RouterPath {

    object UserCenter {
        const val PATH_REGIST = "/userCenter/regist"
    }


}
```

```kotlin
在要访问的activity前面添加路径  这里我配置的是存放在一个固定的文件中 
@Route(path = RouterPath.UserCenter.PATH_REGIST)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView {

 //   override fun injectComponent() {
        //DaggerUserComponent.builder().activityComponent(activityComponent).uSerModule(USerModu//le()).build().inject(this)
   //     mPresenter.mView = this

  //  }
 
```

在需要访问的地方,可以调用

```kotlin
 ARouter.getInstance().build(RouterPath.UserCenter.PATH_REGIST).navigation()
 //或者是带参数传递 
 ARouter.getInstance().build(RouterPath.UserCenter.PATH_REGIST)//这个地方用withXXX()添加传递的参数 .navigation()

```

在接受的类中处理携带的参数

```kotlin
 @Route(path = RouterPath.UserCenter.PATH_REGIST)
class OrderConfirmActivity : BaseMvpActivity<OrderPresenter>(),OrderView {
    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0


```

不要忘了在Application中初始化 ARouter

```kotlin
Arouter.init(this)
```


#### [Room](https://developer.android.google.cn/training/data-storage/room)数据库集成

>  Room 持久性库在 SQLite 的基础上提供了一个抽象层，让用户能够在充分利用
>  SQLite 的强大功能的同时，获享更强健的数据库访问机制。

##### 依赖

```kotlin
 def room_version = "2.2.5"

      implementation "androidx.room:room-runtime:$room_version"
      annotationProcessor "androidx.room:room-compiler:$room_version"
      
```

> 我使用的是kotlin开发,注意：对于基于 Kotlin 的应用，请确保使用
> kapt，而不是 annotationProcessor。您还应添加 kotlin-kapt 插件。

##### 可以配置编译选项

> Room 具有以下注释处理器选项：
>
> 1. room.schemaLocation：[配置并启用将数据库架构导出到给定目录中的 JSON 文件的功能](https://developer.android.google.cn/training/data-storage/room/migrating-db-versions#export-schema)。
> 2. room.incremental：启用 Gradle 增量注释处理器。
>    3.room.expandProjection：配置 Room
>    以重写查询，使其顶部星形投影在展开后仅包含 DAO
>    方法返回类型中定义的列。

代码段举例:

```kotlin
android {
    ...
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [
                    "room.schemaLocation":"$projectDir/schemas".toString(),
                    "room.incremental":"true",
                    "room.expandProjection":"true"]
            }
        }
    }
}
```

##### Room三个主要组件

> 应用使用 Room 数据库来获取与该数据库关联的数据访问对象
> (DAO)。然后，应用使用每个 DAO
> 从数据库中获取实体，然后再将对这些实体的所有更改保存回数据库中。最后，应用使用实体来获取和设置与数据库中的表列相对应的值。
> ![room架构图](https://img-blog.csdnimg.cn/20200616114400673.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lBTkdXRUlRSUFP,size_16,color_FFFFFF,t_70)

###### **数据库**


> 包含数据库持有者，并作为应用已保留的持久关系型数据的底层连接的主要接入点。
> 使用 **@Database** 注释的类应满足以下条件：
>
> >是扩展 **RoomDatabase** 的抽象类。
> >在注释中添加与数据库关联的实体列表。 包含具有 0 个参数且返回使用
> >**@Dao** 注释的类的抽象方法。

> 在运行时，您可以通过调用 Room.databaseBuilder() 或
> Room.inMemoryDatabaseBuilder() 获取 Database 的实例。
> **databaseBuilder( )** 创建一个持久的数据库,保存在用户本地
> **inMemoryDatabaseBuilder()**
> 在内存中创建一个数据库,这个数据库会随着进程的杀死而消失

```kotlin
    @Database(entities = arrayOf(User::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao
    }
    
```

> 在application初始化

```kotlin
    val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()
    
```

###### **Entry**


> 表示数据库中的表,使用@Entry注解标注 , @Entity 注释的 tableName
> 属性可以自定义表名,SQLite 中的表名称不区分大小写。

```kotlin
    @Entity (tableName = "users")
    data class User(
        @PrimaryKey var id: Int,
        var firstName: String?,
        var lastName: String?
    )
    
```

> 使用主键 : 一个Entry中至少需要一个主键,我们使用**@PrimaryKey** 来注释.
> 自增类型的主键,则可以设置 @PrimaryKey 的 autoGenerate 属性。
> 复合主键,使用 @Entity 注释的 primaryKeys 属性

```kotlin
  @Entity(primaryKeys = arrayOf("firstName", "lastName"))
    data class User(
        val firstName: String?,
        val lastName: String?
    )
```

> 同时可以自定义属性字段名 ,使用@ColumnInfo注释

```kotlin
    @Entity(tableName = "users")
    data class User (
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "first_name") val firstName: String?,
        @ColumnInfo(name = "last_name") val lastName: String?
    )
    
```

> 忽略字段 @Ignore
> ,Room数据库在创建的时候会为每一个字段对应在数据库创建一列,如果不想entry中的某一个字段保留在数据表中,可以忽略

```kotlin
   @Entity
    data class User(
        @PrimaryKey val id: Int,
        val firstName: String?,
        val lastName: String?,
        @Ignore val picture: Bitmap?
    )
```

> 如果涉及到entry继承,我们想要忽略父类中的字段 ,需要使用 @Entity 属性的
> ignoredColumns 属性,这样会更加简洁

```kotlin
    open class User {
        var picture: Bitmap? = null
    }

    @Entity(ignoredColumns = arrayOf("picture"))
    data class RemoteUser(
        @PrimaryKey val id: Int,
        val hasVpn: Boolean
    ) : User()
    
```

###### **DAO**

> 包含用于访问数据库的方法。

```kotlin
    @Dao
    interface UserDao {
        @Query("SELECT * FROM user")
        fun getAll(): List<User>

        @Query("SELECT * FROM user WHERE uid IN (:userIds)")
        fun loadAllByIds(userIds: IntArray): List<User>

        @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
               "last_name LIKE :last LIMIT 1")
        fun findByName(first: String, last: String): User

        @Insert
        fun insertAll(vararg users: User)

        @Delete
        fun delete(user: User)
    }
    
```

> 在DAO中定义方法方便使用

> insert : 当您创建 DAO 方法并使用 @Insert 对其进行注释时，Room
> 会生成一个实现，该实现在单个事务中将所有参数插入到数据库中。
> 方法的返回值是当前entry主键id,可以单个插入返回一个id,也可以批量插入,返回一个List集合Id


```kotlin
    @Dao
    interface MyDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertUsers(vararg users: User)

        @Insert
        fun insertBothUsers(user1: User, user2: User)

        @Insert
        fun insertUsersAndFriends(user: User, friends: List<User>)
    }
    
```

> update ,方法的返回值是表中更新的行数

```kotlin
    @Dao
    interface MyDao {
        @Update
        fun updateUsers(vararg users: User)
    }
    
```

> Delete
> 会从数据库中删除一组以参数形式给出的实体。这个是根据主键来查找删除的
> ,方法的返回值是int,表示删除的行数

```kotlin
    @Dao
    interface MyDao {
        @Delete
        fun deleteUsers(vararg users: User)
    }
    
```

> Query 是最常用的,

```kotlin
    @Dao
    interface MyDao {
        @Query("SELECT * FROM user")
        fun loadAllUsers(): Array<User>
    }
    
```

> //条件查询 将参数传递到sql中, 使用 : 将参数绑定到sql中

```kotlin
    @Dao
    interface MyDao {
    //条件查询 将参数传递到sql中, 使用 :
        @Query("SELECT * FROM user WHERE age > :minAge")
        fun loadAllUsersOlderThan(minAge: Int): Array<User>
    }
    
```

```kotlin
    @Dao
    interface MyDao {
        @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
        fun loadAllUsersBetweenAges(minAge: Int, maxAge: Int): Array<User>

        @Query("SELECT * FROM user WHERE first_name LIKE :search " +
               "OR last_name LIKE :search")
        fun findUserWithName(search: String): List<User>
    }
    
```

> 返回列的子集
> 大多数情况下，您只需获取实体的几个字段。例如，您的界面可能仅显示用户的名字和姓氏，而不是用户的每一条详细信息。通过仅提取应用界面中显示的列，您可以节省宝贵的资源，并且您的查询也能更快完成。
>
> 借助 Room，您可以从查询中返回任何基于 Java
> 的对象，前提是结果列集合会映射到返回的对象。例如，您可以创建以下基于
> Java 的普通对象 (POJO) 来获取用户的名字和姓氏：

```kotlin
    data class NameTuple(
        @ColumnInfo(name = "first_name") val firstName: String?,
        @ColumnInfo(name = "last_name") val lastName: String?
    )
    
```

现在，您可以在查询方法中使用此 POJO：

```kotlin
    @Dao
    interface MyDao {
        @Query("SELECT first_name, last_name FROM user")
        fun loadFullName(): List<NameTuple>
    }
    
```

> Room 知道该查询会返回 first_name 和 last_name
> 列的值，并且这些值会映射到 NameTuple 类的字段。因此，Room
> 可以生成正确的代码。如果查询返回太多的列，或者返回 NameTuple
> 类中不存在的列，则 Room 会显示一条警告。


[使用RxJava进行响应式查询](https://developer.android.google.cn/training/data-storage/room/accessing-data#query-rxjava)

```kotlin
    dependencies {
        def room_version = "2.1.0"
        implementation 'androidx.room:room-rxjava2:$room_version'
    }
    
```

> Room 为 RxJava2 类型的返回值提供了以下支持： @Query 方法：Room 支持
> Publisher、Flowable 和 Observable 类型的返回值。 @Insert、@Update 和
> @Delete 方法：Room 2.1.0 及更高版本支持 Completable、Single<T> 和
> Maybe<T> 类型的返回值。

```kotlin
    @Dao
    interface MyDao {
        @Query("SELECT * from user where id = :id LIMIT 1")
        fun loadUserById(id: Int): Flowable<User>

        // Emits the number of users added to the database.
        @Insert
        fun insertLargeNumberOfUsers(users: List<User>): Maybe<Int>

        // Makes sure that the operation finishes successfully.
        @Insert
        fun insertLargeNumberOfUsers(varargs users: User): Completable

        /* Emits the number of users removed from the database. Always emits at
           least one user. */
        @Delete
        fun deleteAllUsers(users: List<User>): Single<Int>
    }
    
```

....................... .........................
