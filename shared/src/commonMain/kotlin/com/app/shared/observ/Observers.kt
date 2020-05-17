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

interface Observer<T> {
    val subscriber: Subscriber<T>
    fun didChange(value: T)
    fun collect(function: (T) -> Unit) {
        this.subscriber.collect(function)
    }
}

class SimpleObserver<T>(override val subscriber: Subscriber<T> = SimpleSubscriber()): Observer<T> {

    override fun didChange(value: T) {
        subscriber.didChange(value)
    }
}

fun <T, U> Observer<T>.map(transform: (T) -> U): Observer<U> {
    return SimpleObserver(this.subscriber.map(transform))
}

fun <T> Observer<T>.filter(transform: (T) -> Boolean): Observer<T> {
    return SimpleObserver(this.subscriber.filter(transform))
}

fun <T> Observer<T?>.filterNotNull(): Observer<T> {
    return SimpleObserver(this.subscriber.filterNotNull())
}

interface Emitter<T> {
    fun add(observer: Observer<T>)
    fun remove(observer: Observer<T>)
    fun emit(value: T)
    fun currentObservers(): List<Observer<T>>
    fun observe(): Observer<T>
    fun cleanup()
}

class InfiniteEmitter<T>: Emitter<T> {

    private val observers = mutableListOf<Observer<T>>()

    override fun add(observer: Observer<T>) {
        observers.add(observer)
    }

    override fun remove(observer: Observer<T>) {
        observers.remove(observer)
    }

    override fun emit(value: T) {
        observers.forEach { it.didChange(value = value) }
    }

    override fun currentObservers(): List<Observer<T>> {
        return observers.toList()
    }

    override fun observe(): Observer<T> {
        val observer = SimpleObserver<T>()
        add(observer)
        return observer
    }

    override fun cleanup() {
        observers.clear()
    }
}