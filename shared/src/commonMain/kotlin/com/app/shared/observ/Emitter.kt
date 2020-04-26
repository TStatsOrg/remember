package com.app.shared.observ

interface Emitter <T> {
    fun emit(value: T)
}

open class InfiniteEmitter <T> (protected open val observer: Observer<T>) : Emitter<T> {

    override fun emit(value: T) {
        observer.observe(value = value)
    }
}

class ObservableEmitter <T>(): InfiniteEmitter<T> (observer = SimpleObserver<T>()) {
    fun observer(): Observer<T> {
        return this.observer
    }
}

interface Observer<T> {
    fun observe(value: T)
    fun collect(callback: (T) -> Unit)
}

class SimpleObserver <T>: Observer<T> {

    private var call: ((T) -> Unit)? = null

    override fun observe(value: T) {
        call?.invoke(value)
    }

    override fun collect(callback: (T) -> Unit) {
        call = callback
    }
}

fun <T, U> Observer<T>.map(func: (T) -> U): Observer<U> {

    return object : Observer<U> {
        override fun observe(value: U) = Unit
        override fun collect(callback: (U) -> Unit) {
            this@map.collect {
                callback(func(it))
            }
        }
    }
}

fun <T> Observer<T?>.filterNotNull(): Observer<T> {
    return object : Observer<T> {
        override fun observe(value: T) = Unit
        override fun collect(callback: (T) -> Unit) {
            this@filterNotNull.collect {
                it?.let { callback(it) }
            }
        }
    }
}