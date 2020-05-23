package com.app.shared.feature.topics

import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.business.TopicState
import com.app.shared.business.TopicsState
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedTopicsViewModel(
        private val store: Store<MainState>,
        private val calendar: CalendarUtils,
        private val topicsRepository: TopicsRepository,
        private val bookmarkRepository: BookmarkRepository
): TopicsViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

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

    override fun filter(byTopic: TopicState) {
        scope.launch(context = MainDispatcher) {
            val dtos = bookmarkRepository.load()

            val filtered = dtos.filter { it.topic?.id == byTopic.id }

            store.dispatch(
                action = Actions.Bookmark.Filter(
                    bookmarks = filtered,
                    topic = byTopic))
        }
    }

    override fun delete(topicId: Int) {
        scope.launch(context = MainDispatcher) {

            val defaultTopic = TopicDTO.GeneralTopic()

            // don't delete the default topic
            if (defaultTopic.id == topicId) {
                return@launch
            }

            bookmarkRepository.replaceTopic(topicId = topicId, withTopicDTO = defaultTopic)
            topicsRepository.delete(topicId = topicId)
            store.dispatch(action = Actions.Topics.Delete(topicId = topicId))
        }
    }

    override fun observeTopicState(callback: (TopicsState) -> Unit) {
        storeObserver
            .map { it.topics }
            .collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}