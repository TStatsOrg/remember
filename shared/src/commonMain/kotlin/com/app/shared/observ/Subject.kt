package com.app.shared.observ

interface Subscriber<T> {
    var function: ((T) -> Unit)?
    fun collect(callback: (T) -> Unit)
}

class SimpleSubscriber<T>: Subscriber<T> {

    override var function: ((T) -> Unit)? = null

    override fun collect(callback: (T) -> Unit) {
        function = callback
    }
}

fun <T, U> Subscriber<T>.map(transform: (T) -> U): Subscriber<U> {
    return object : Subscriber<U> {
        override var function: ((U) -> Unit)? = null
        override fun collect(callback: (U) -> Unit) {
            this@map.collect {
                callback(transform(it))
            }
        }
    }
}

fun <T> Subscriber<T?>.filterNotNull(): Subscriber<T> {
    return object : Subscriber<T> {
        override var function: ((T) -> Unit)? = null
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

class SimpleObserver2<T>(internal val subscriber: Subscriber<T> = SimpleSubscriber()): Observer2<T> {

    override fun didChange(value: T) {
        subscriber.function?.invoke(value)
    }
}

fun <T, U> SimpleObserver2<T>.map(transform: (T) -> U): SimpleObserver2<U> {
    return SimpleObserver2(this.subscriber.map(transform))
}

fun <T> SimpleObserver2<T?>.filterNotNull(): SimpleObserver2<T> {
    return SimpleObserver2(this.subscriber.filterNotNull())
}

fun <T> SimpleObserver2<T>.collect(function: (T) -> Unit): SimpleObserver2<T> {
    this.subscriber.collect(function)
    return this
}

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