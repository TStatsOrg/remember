package com.app.shared.redux

import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.observ.ObservableEmitter
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

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
    }

    fun register(forResult: StoreResult<S>) = results.add(forResult)
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
 * Transforms our normal store into a flow
 */
fun <S: State> Store<S>.asFlow() = callbackFlow {
    this@asFlow.register {
        offer(element = it)
    }

    awaitClose { cancel() }
}

/**
 * Default store
 */
val store = Store(initialState = AppState(), reducer = AppStateReducer)