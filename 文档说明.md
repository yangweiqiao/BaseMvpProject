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

