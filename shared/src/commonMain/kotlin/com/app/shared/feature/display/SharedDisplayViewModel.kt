package com.app.shared.feature.display

import com.app.shared.business.Actions
import com.app.shared.business.DisplayState
import com.app.shared.business.MainState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.MLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SharedDisplayViewModel(
    private val store: Store<MainState>,
    private val process: RawDataProcess,
    private val bookmarkRepository: BookmarkRepository
): DisplayViewModel {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun loadRssItem(rssItemId: Int) {
        scope.launch(context = DispatcherFactory.main()) {
            store.dispatch(action = Actions.Display.Show(id = rssItemId))

            val displayItem = store.state.display
            val url = displayItem.url

            process.process(capture = url) {
                MLogger.log("GABBOX ==> $it")
            }
        }
    }

    override fun save() {
        scope.launch(context = DispatcherFactory.main()) {
            val item = store.state.display

            val bookmarkDTO = object : BookmarkDTO.LinkBookmarkDTO {
                override val url: String = item.url
                override val title: String? = item.title
                override val caption: String? = item.caption
                override val icon: String? = null
                override val id: Int = item.id
                override val date: Long = item.date
                override val topic: TopicDTO? = TopicDTO.GeneralTopic()
            }

            bookmarkRepository.save(dto = bookmarkDTO)
            store.dispatch(action = Actions.Bookmark.Add(dto = bookmarkDTO))
        }
    }

    override fun delete() {
        scope.launch(context = DispatcherFactory.main()) {

            val item = store.state.display

            bookmarkRepository.delete(bookmarkId = item.id)
            store.dispatch(action = Actions.Bookmark.Delete(bookmarkId = item.id))
        }
    }

    override fun observeDisplayState(callback: (DisplayState) -> Unit) {
        storeObserver
            .map { it.display }
            .collect(callback)
    }

    override fun cleanup() {
        store.remove(observer = storeObserver)
    }
}