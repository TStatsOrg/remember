package com.app.shared.feature.topics

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.TopicsState
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.redux.toEmitter
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedTopicsViewModel(
    private val store: Store<AppState>,
    private val calendar: CalendarUtils,
    private val topicsRepository: TopicsRepository
): TopicsViewModel {

    private val scope: CoroutineScope = provideViewModelScope()

    override fun loadTopics() {
        scope.launch(context = MainDispatcher) {

            // start load
            store.dispatch(action = Actions.Topics.Load.Start(time = calendar.getTime()))

            // get all topics
            val dtos = topicsRepository.load()

            // send to store
            store.dispatch(action = Actions.Topics.Load.Success(time = calendar.getTime(), topics = dtos))
        }
    }

    override fun filter(byName: String) {
        scope.launch(context = MainDispatcher) {
            // start
            val dtos = topicsRepository.load()
            val filtered = dtos.filter { it.name.contains(byName, ignoreCase = true) }

            // send to store
            store.dispatch(action = Actions.Topics.Load.Success(time = calendar.getTime(), topics = filtered))
        }
    }

    override fun observeTopicState(callback: (TopicsState) -> Unit) {
        store.toEmitter()
            .observer()
            .map { it.topics }
            .collect(callback)
    }
}