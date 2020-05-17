package com.app.shared.observ

interface Subscriber<T> {
    fun collect(callback: (T) -> Unit)
}

class SimpleSubscriber<T>: Subscriber<T> {

    internal var function: ((T) -> Unit)? = null

    override fun collect(callback: (T) -> Unit) {
        function = callback
    }
}

fun <T, U> Subscriber<T>.map(transform: (T) -> U): Subscriber<U> {
    return object : Subscriber<U> {
        override fun collect(callback: (U) -> Unit) {
            this@map.collect {
                callback(transform(it))
            }
        }
    }
}

fun <T> Subscriber<T?>.filterNotNull(): Subscriber<T> {
    return object : Subscriber<T> {
        override fun collect(callback: (T) -> Unit) {
            this@filterNotNull.collect {
                it?.let { callback(it) }
            }
        }
    }
}

interface Observer2<T> {
    fun didChange(value: T)
}

class SimpleObserver2<T>(private val func: ((T) -> Unit)? = null): Observer2<T> {

    override fun didChange(value: T) {
        func?.invoke(value)
    }
}

class SubscribingObserver2<T>: Observer2<T> {

    var subscriber: SimpleSubscriber<T> = SimpleSubscriber()

    override fun didChange(value: T) {
        subscriber.function?.invoke(value)
    }
}

//fun <T, U> SubscribingObserver2<T>.map(transform: (T) -> U): SubscribingObserver2<U> {
//    val newSub = SubscribingObserver2<U>()
//    newSub.subscriber = this.subscriber.map(transform)
//}

interface Emitter2<T> {
    fun add(observer: Observer2<T>)
    fun remove(observer: Observer2<T>)
    fun emit(value: T)
}

class InfiniteEmitter2<T>: Emitter2<T> {

    val observers = mutableListOf<Observer2<T>>()

    override fun add(observer: Observer2<T>) {
        observers.add(observer)
    }

    override fun remove(observer: Observer2<T>) {
        observers.remove(observer)
    }

    override fun emit(value: T) {
        observers.forEach { it.didChange(value = value) }
    }
}