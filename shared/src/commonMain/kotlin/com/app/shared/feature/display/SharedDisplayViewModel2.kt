package com.app.shared.feature.display

import com.app.shared.business.Actions
import com.app.shared.business.DisplayState
import com.app.shared.business.Either
import com.app.shared.business.MainState
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.provideViewModelScope
import com.app.shared.data.capture.HTMLDataProcess
import com.app.shared.data.dto.TopicDTO
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.observ.map
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.toDTO
import com.app.shared.utils.toState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedDisplayViewModel2(
    private val store: Store<MainState>,
    private val bookmarkRepository: BookmarkRepository,
    private val calendarUtils: CalendarUtils,
    private val htmlDataProcess: HTMLDataProcess
): DisplayViewModel2 {

    private val scope: CoroutineScope = provideViewModelScope()
    private val storeObserver = store.observe()

    override fun startLoad() {
        scope.launch(context = DispatcherFactory.main()) {
            store.dispatch(action = Actions.Display.Load.Start(time = calendarUtils.getTime()))
        }
    }

    override fun finishLoad(url: String, content: String) {
        scope.launch(context = DispatcherFactory.main()) {

            val result: Either<HTMLDataProcess.Result> = withContext(context = DispatcherFactory.io()) {
                htmlDataProcess.process(html = content)
            }

            when (result) {
                is Either.Success -> store.dispatch(
                    action = Actions.Display.Load.Success(
                        url = url,
                        title = result.data.title,
                        caption = result.data.caption,
                        icon = result.data.icon,
                        time = calendarUtils.getTime()
                    ))
                is Either.Failure -> store.dispatch(
                    action = Actions.Display.Load.Error(
                        time = calendarUtils.getTime(),
                        error = result.error
                    ))
            }
        }
    }

    override fun save() {
        scope.launch(context = DispatcherFactory.main()) {
            val item = store.state.display.item?.toDTO(withTopic = TopicDTO.GeneralTopic().toState())

            item?.let {
                bookmarkRepository.save(dto = item)
                store.dispatch(action = Actions.Bookmark.Add(dto = item))
            }
        }
    }

    override fun delete() {
        scope.launch(context = DispatcherFactory.main()) {

            val item = store.state.display.item

            item?.let {
                bookmarkRepository.delete(bookmarkId = item.id)
                store.dispatch(action = Actions.Bookmark.Delete(bookmarkId = item.id))
            }
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