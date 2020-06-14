## 基础项目创建
1. 创建项目 App
2. 创建lib base 和provider
3. 添加依赖关系 App->provider ->base
4. base中创建包  common 通用包 data 数据包,其中包括net网络请求 protocol 实体类包 ; ext: kotlin扩展方法包
      presenter包:mvp层基类,包括presenter和view层的包; rx:支持rx相关;
      ui包:activity和fragment的基类; widgets:ui自定义组件
5. provider中创建包: common;    event:作为中间实践传递;router:模块之间的相互通信和接口调用;
6. App中创建包:common  ui

## 搭建模块化
### 模块化
1.  Application和library
     application是作为应用程序启动 :
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
   对应两套 AndroidManifest,一套是作为application时候使用,一套是作为lib时候使用;
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
动态加载模块类型
   使用UserCenter测试动态加载模块

### 基础模块封装
~~视图层框架 : butterKnife
在kotlin多模块下的工程中不能使用,报错 unable to find resource ID 错误原因: module中资源id与主工程资源ID不一致~~
1. android-extensions


> 视图绑定,可直接使用xml中id操作该控件
> 插件级别,无需引用第三方库
> 无需定义变量,极大减少代码
> 适用于activity fragment adapter 和自定义view
> apply plugin: 'kotlin-android-extensions'
使用 : 第一步 添加插件 第二步: 直接操作xml中的id

2. anko  kotlin提供的一些方便操作的库


> Anko Commons 工具库 ,例如 toast intent跳转
> Anko layouts 布局
> Anko SQLLite 数据库
> Anko coroutines 协程

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

定义baseMvpActivity来承载p和v ,持有basepresenter的应用 泛型实现 ,实现BaseView

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

> RxKotlin  基于RxJava的扩展扩,以kotlin的风格提供大量的扩展方法
> 响应式编程
> 观察者模式
> 引入依赖包` api "io.reactivex:rxkotlin:$rx_koltin" `
> [github](https://github.com/ReactiveX/RxKotlin)
##### RxAndroid
>  RxAndroid 基于rxjava,可以优雅的处理异步请求   更好的兼容android的特性,例如主线程,ui事件
> 引入依赖包`  api "io.reactivex:rxandroid:$rx_android"`

测试
1.创建service层

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

Subscriber 改造  抽取基类,后续只需要实现这里的onNext方法

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
