package com.app.shared.feature.topics

import com.app.shared.business.*
import com.app.shared.coroutines.DispatcherFactory
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
        scope.launch(context = DispatcherFactory.main()) {

            // start load
            store.dispatch(action = Actions.Topics.Load.Start(time = calendar.getTime()))

            // get all topics
            val dtos = topicsRepository.load()

            // send to store
            store.dispatch(action = Actions.Topics.Load.Success(time = calendar.getTime(), topics = dtos))
        }
    }

    override fun filter(byTopic: TopicState) {
        scope.launch(context = DispatcherFactory.main()) {
            store.dispatch(action = Actions.Bookmark.Filter(topic = byTopic))
        }
    }

    override fun delete(topicId: Int) {
        scope.launch(context = DispatcherFactory.main()) {

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

    override fun observeTopicState(callback: (TopicsState, List<BookmarkState>) -> Unit) {
        storeObserver
            .map { Pair(it.topics, it.allBookmarks) }
            .collect {
                callback(it.first, it.second)
            }
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}