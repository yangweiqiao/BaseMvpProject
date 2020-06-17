package com.example.provider.event

import com.example.baselibrary.log.YLogUtils
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subscriptions.CompositeSubscription

/**
 * Created by yangw on 2018/4/28.
 * author by ywq
 * desc :事件总线,基于Rxjava实现
 * 使用: 发送事件  Bus.send(XXXEvent())
 * 注册接受事件 :     Bus.observe<XXXEvent>().subscribe { dosomething }.registerInBus(this)
 * 注销事件 :  Bus.unregister(this)
 */
object Bus {
    private val TAG = javaClass.simpleName

    /**
     * 用于保存总线事件的所有订阅并可以正确地取消订阅
     */
    private val subscriptionsMap: HashMap<Any, CompositeSubscription?> by lazy {
        HashMap<Any, CompositeSubscription?>()
    }

    /**
     * 避免直接使用此属性，只因在内联中使用而暴露
     */
    val bus = SerializedSubject(PublishSubject.create<Any>())

    /**
     * 发送一个事件,可以接受在任意线程
     */
    fun send(event: Any) {
        bus.onNext(event)
    }

    /**
     * 订阅泛型的事件总线,可以在任意线程调用
     */
    inline fun <reified T : Any> observe(): Observable<T> {
        return bus.ofType(T::class.java)
    }

    /**
     * 反注册,在推出界面的时候调用
     * @param subscriber  需要注销的事件
     */
    fun unregister(subscriber: Any) {
        val compositeSubscription = subscriptionsMap[subscriber]
        if (compositeSubscription == null) {
            YLogUtils.e(" $TAG :这里就是之前没有反注册,然后重复注册,Trying to unregister subscriber that wasn't registered")
        } else {
            compositeSubscription.clear()
            subscriptionsMap.remove(subscriber)
        }
    }

    /**
     * 注册事件
     */
    internal fun register(subscriber: Any, subscription: Subscription) {
        var compositeSubscription = subscriptionsMap[subscriber]
        if (compositeSubscription == null) {
            compositeSubscription = CompositeSubscription()
        }
        compositeSubscription.add(subscription)
        subscriptionsMap[subscriber] = compositeSubscription
    }
}

/**
 * Registers the subscription to correctly unregister it later to avoid memory leaks
 * @param subscriber subscriber object that owns this subscription
 */
fun Subscription.registerInBus(subscriber: Any) {
    Bus.register(subscriber, this)
}