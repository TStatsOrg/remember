package com.app.shared.observ

interface Subscriber<T> {
    var function: ((T) -> Unit)?
    fun collect(callback: (T) -> Unit)
    fun didChange(value: T)
}

class SimpleSubscriber<T>: Subscriber<T> {

    override var function: ((T) -> Unit)? = null

    override fun collect(callback: (T) -> Unit) {
        function = callback
    }

    override fun didChange(value: T) {
        function?.invoke(value)
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
        override fun didChange(value: U) {
            function?.invoke(value)
        }
    }
}

fun <T> Subscriber<T>.filter(transform: (T) -> Boolean): Subscriber<T> {
    return object : Subscriber<T> {
        override var function: ((T) -> Unit)? = null
        override fun collect(callback: (T) -> Unit) {
            this@filter.collect {
                if (transform(it)) {
                    callback(it)
                }
            }
        }
        override fun didChange(value: T) {
            function?.invoke(value)
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
        override fun didChange(value: T) {
            function?.invoke(value)
        }
    }
}

interface Observer2<T> {
    val subscriber: Subscriber<T>
    fun didChange(value: T)
}

class SimpleObserver2<T>(override val subscriber: Subscriber<T> = SimpleSubscriber()): Observer2<T> {

    override fun didChange(value: T) {
        subscriber.didChange(value)
    }
}

fun <T, U> Observer2<T>.map(transform: (T) -> U): Observer2<U> {
    return SimpleObserver2(this.subscriber.map(transform))
}

fun <T> Observer2<T>.filter(transform: (T) -> Boolean): Observer2<T> {
    return SimpleObserver2(this.subscriber.filter(transform))
}

fun <T> Observer2<T?>.filterNotNull(): Observer2<T> {
    return SimpleObserver2(this.subscriber.filterNotNull())
}

fun <T> Observer2<T>.collect(function: (T) -> Unit): Observer2<T> {
    this.subscriber.collect(function)
    return this
}

interface Emitter2<T> {
    fun add(observer: Observer2<T>)
    fun remove(observer: Observer2<T>)
    fun emit(value: T)
    fun observe(): Observer2<T>
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

    override fun observe(): Observer2<T> {
        val observer = SimpleObserver2<T>()
        add(observer)
        return observer
    }
}