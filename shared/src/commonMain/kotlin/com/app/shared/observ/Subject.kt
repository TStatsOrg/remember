package com.app.shared.observ

interface Subject<T> {
    fun emit(value: T)
    fun collect(callback: (T) -> Unit): Subject<T>
}

class InfiniteSubject<T>: Subject<T> {

    private val observers = mutableListOf<(T) -> Unit>()

    override fun emit(value: T) {
        observers.forEach { it.invoke(value) }
    }

    override fun collect(callback: (T) -> Unit): Subject<T> {
        observers.add(callback)
        return this
    }
}