package com.app.shared.redux

import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.observ.ObservableEmitter
import com.app.shared.utils.MLogger

interface Action
interface State

typealias Reducer<S> = (state: S, action: Action) -> S

typealias StoreResult<S> = (S) -> Unit

class Store<S: State> (initialState: S, private val reducer: Reducer<S>) {

    private val results = mutableListOf<StoreResult<S>>()

    var state: S = initialState
        private set

    fun dispatch(action: Action) {
        state = reducer(state, action)
        results.forEach { it.invoke(state) }
        MLogger.log("GABBOX2: Store dispatches to ${results.size}")
    }

    fun register(forResult: StoreResult<S>) = results.add(forResult)

    fun unregister(forResult: StoreResult<S>) = results.remove(forResult)
}

/**
 * Transform our normal store to an Emitter
 */
fun <S: State> Store<S>.toEmitter(): ObservableEmitter<S> {
    val emitter = ObservableEmitter<S>()
    this.register {
        emitter.emit(value = it)
    }

    return emitter
}

/**
 * Default store
 */
val store = Store(initialState = AppState(), reducer = AppStateReducer)