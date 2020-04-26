package com.app.shared.feature.topics

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.business.TopicsState
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.redux.Store
import com.app.shared.redux.asFlow
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SharedTopicsViewModel(
    private val store: Store<AppState>,
    private val calendar: CalendarUtils,
    private val topicsRepository: TopicsRepository
): TopicsViewModel {

    private val scope: CoroutineScope = provideViewModelScope()

    override fun loadTopics(forBookmarkId: Int?) {
        scope.launch(context = MainDispatcher) {

            // start load
            store.dispatch(action = Actions.Topics.Load.Start(time = calendar.getTime()))

            // get all topics
            val dtos = topicsRepository.load()

            // send to store
            when (forBookmarkId) {
                is Int -> store.dispatch(action = Actions.Topics.Load.Success.ForEditing(
                    time = calendar.getTime(),
                    topics = dtos,
                    bookmarkId = forBookmarkId
                ))
                else -> store.dispatch(action = Actions.Topics.Load.Success.ForViewing(
                    time = calendar.getTime(),
                    topics = dtos
                ))
            }
        }
    }

    override fun observeTopicState(callback: (TopicsState) -> Unit) {
        scope.launch(context = MainDispatcher) {
            store.asFlow()
                .flowOn(DefaultDispatcher)
                .map { it.topics }
                .collect {
                    callback(it)
                }
        }
    }
}