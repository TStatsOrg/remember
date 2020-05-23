package com.app.shared.redux

import com.app.shared.business.MainState
import com.app.shared.business.AppStateReducer
import com.app.shared.observ.Emitter
import com.app.shared.observ.InfiniteEmitter
import com.app.shared.observ.Observer
import com.app.shared.utils.MLogger

interface Action
interface AppState

typealias Reducer<S> = (state: S, action: Action) -> S

class Store<S: AppState> (initialState: S, private val reducer: Reducer<S>) {

    val emitter: Emitter<S> = InfiniteEmitter()

    var state: S = initialState
        private set

    fun dispatch(action: Action) {
        state = reducer(state, action)
        emitter.emit(value = state)
    }

    fun observe(): Observer<S> = emitter.observe()

    fun remove(observer: Observer<S>) = emitter.remove(observer = observer)
}

/**
 * Default store
 */
val store = Store(initialState = MainState(), reducer = AppStateReducer)