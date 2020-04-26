package com.app.shared.feature.addtopic

import com.app.shared.business.Actions
import com.app.shared.business.AppState
import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.redux.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedAddTopicViewModel(
    private val store: Store<AppState>,
    private val topicsRepository: TopicsRepository
): AddTopicViewModel {

    private val scope: CoroutineScope = provideViewModelScope()

    private var observer: (() -> Unit)? = null

    override fun addTopic(name: String) {
        scope.launch(context = MainDispatcher) {

            // produce dto
            val dto = object : TopicDTO {
                override val id: Int = name.hashCode()
                override val name: String = name
            }

            // save dto
            topicsRepository.save(dto = dto)
            store.dispatch(action = Actions.Topics.Add(topic = dto))

            // all done
            observer?.invoke()
        }
    }

    override fun observeTopicSaved(callback: () -> Unit) {
        observer = callback
    }
}