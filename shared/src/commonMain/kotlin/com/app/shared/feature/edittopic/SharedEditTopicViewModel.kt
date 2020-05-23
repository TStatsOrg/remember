package com.app.shared.feature.edittopic

import com.app.shared.business.Actions
import com.app.shared.business.EditTopicState
import com.app.shared.business.MainState
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.observ.InfiniteEmitter
import com.app.shared.observ.filterNotNull
import com.app.shared.observ.map
import com.app.shared.redux.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedEditTopicViewModel(
    private val store: Store<MainState>,
    private val topicsRepository: TopicsRepository
): EditTopicViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()
    private val emitter = InfiniteEmitter<Boolean>()

    override fun loadEditableTopic(forTopicId: Int) {
        scope.launch(context = MainDispatcher) {
            store.dispatch(action = Actions.Topics.Edit(topicId = forTopicId))
        }
    }

    override fun updateTopic(topicId: Int, name: String) {
        scope.launch(context = MainDispatcher) {
            store.dispatch(action = Actions.Topics.Update(topicId = topicId, newName = name))

            val newDto = object : TopicDTO {
                override val id: Int = topicId
                override val name: String = name
            }

            topicsRepository.save(dto = newDto)

            emitter.emit(value = true)
        }
    }

    override fun observeEditTopicState(callback: (EditTopicState) -> Unit) {
        storeObserver
            .map { it.editTopic }
            .filterNotNull()
            .collect(callback)
    }

    override fun observeTopicUpdated(callback: (Boolean) -> Unit) {
        emitter.observe().collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
        emitter.cleanup()
    }
}