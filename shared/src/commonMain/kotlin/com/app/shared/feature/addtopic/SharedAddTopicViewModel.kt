package com.app.shared.feature.addtopic

import com.app.shared.business.Actions
import com.app.shared.business.MainState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.observ.InfiniteEmitter
import com.app.shared.redux.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedAddTopicViewModel(
    private val store: Store<MainState>,
    private val topicsRepository: TopicsRepository
): AddTopicViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val emitter = InfiniteEmitter<Boolean>()

    override fun addTopic(name: String) {
        scope.launch(context = DispatcherFactory.main()) {

            if (name.isEmpty()) {
                return@launch
            }

            // produce dto
            val dto = object : TopicDTO {
                override val id: Int = name.hashCode()
                override val name: String = name
            }

            // save dto
            topicsRepository.save(dto = dto)
            store.dispatch(action = Actions.Topics.Add(topic = dto))

            // all done
            emitter.emit(value = true)
        }
    }

    override fun observeTopicSaved(callback: (Boolean) -> Unit) {
        emitter.observe().collect(callback)
    }

    override fun cleanup() {
        emitter.cleanup()
    }
}