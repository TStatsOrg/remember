//package com.app.shared.observ
//
//interface Emitter2 <T> {
//    fun emit(value: T)
//    fun attach(observer: Observer2<T>)
//    fun detach(observer: Observer2<T>)
//}
//
//open class InfiniteEmitter2 <T>: Emitter2<T> {
//
//    val observers: MutableList<Observer2<T>> = mutableListOf()
//
//    override fun emit(value: T) {
//        observers.forEach { it.observe(value = value) }
//    }
//
//    override fun attach(observer: Observer2<T>) {
//        observers.add(observer)
//    }
//
//    override fun detach(observer: Observer2<T>) {
//        observers.remove(observer)
//    }
//}
//
//open class ObservableEmitter2 <T>: InfiniteEmitter2<T>() {
//
//    fun addObserver(): Observer2<T> {
//        val observer = SimpleObserver2(emitter = this)
//        attach(observer = observer)
//        return observer
//    }
//}
//
//interface Observer2<T> {
//
//    val emitter: Emitter2<T>
//
//    fun observe(value: T)
//    fun collect(callback: (T) -> Unit): Observer2<T>
//}
//
//class SimpleObserver2 <T>(override val emitter: Emitter2<T>): Observer2<T> {
//
//    private var call: ((T) -> Unit)? = null
//
//    override fun observe(value: T) {
//        call?.invoke(value)
//    }
//
//    override fun collect(callback: (T) -> Unit): Observer2<T> {
//        call = callback
//        return this
//    }
//}
//
//fun <T, U> Observer2<T>.map(func: (T) -> U): Observer2<U> {
//
//
//
//    return object : Observer2<U> {
//        override val emitter: Emitter2<U> = this@map.emitter
//        override fun observe(value: U) = Unit
//        override fun collect(callback: (U) -> Unit): Observer2<U> {
//            this@map.collect {
//                callback(func(it))
//            }
//
//            return this
//        }
//    }
//}
//
//fun <T> Observer2<T?>.filterNotNull(): Observer2<T> {
//    return object : Observer2<T> {
//        override fun observe(value: T) = Unit
//        override fun collect(callback: (T) -> Unit): Observer2<T> {
//            this@filterNotNull.collect {
//                it?.let { callback(it) }
//            }
//
//            return this
//        }
//    }
//}