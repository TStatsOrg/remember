package com.app.shared.feature.addtopic

import com.app.shared.coroutines.MainDispatcher
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.TopicsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedAddTopicViewModel(
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

            // all done
            observer?.invoke()
        }
    }

    override fun observeTopicSaved(callback: () -> Unit) {
        observer = callback
    }
}