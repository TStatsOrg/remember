package com.app.remember

import android.content.Context
import android.content.Intent
import com.app.dependencies.data.dao.RealmDatabase
import com.app.dependencies.data.utils.AndroidImageLoader
import com.app.dependencies.data.utils.GlideImageLoader
import com.app.dependencies.navigation.Navigation
import com.app.feature.bookmark.edit.EditBookmarksAdapter
import com.app.feature.hub.adapters.BookmarksAdapter
import com.app.feature.preview.PreviewsAdapter
import com.app.feature.topics.TopicsAdapter
import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.data.capture.AndroidDataProcess
import com.app.shared.data.capture.IntentDataCapture
import com.app.shared.data.capture.RawDataCapture
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.dao.Database
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.SharedBookmarkRepository
import com.app.shared.data.repository.SharedTopicsRepository
import com.app.shared.data.repository.TopicsRepository
import com.app.shared.feature.addtopic.AddTopicViewModel
import com.app.shared.feature.addtopic.SharedAddTopicViewModel
import com.app.shared.feature.editbookmark.EditBookmarkViewModel
import com.app.shared.feature.editbookmark.SharedEditBookmarkViewModel
import com.app.shared.feature.mainhub.MainHubViewModel
import com.app.shared.feature.mainhub.SharedMainHubViewModel
import com.app.shared.feature.preview.PreviewViewModel
import com.app.shared.feature.preview.SharedPreviewViewModel
import com.app.shared.feature.topics.SharedTopicsViewModel
import com.app.shared.feature.topics.TopicsViewModel
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.SystemCalendarUtils
import org.koin.dsl.module

class AppDependencyProvider(private val appContext: Context) {

    val module = module {
        // general
        single { Store(initialState = AppState(), reducer = AppStateReducer) }
        single<Database> { RealmDatabase(context = appContext) }
        single<Navigation> { AppNavigation() }

        // utils
        single<CalendarUtils> { SystemCalendarUtils() }
        single<RawDataCapture<Intent>> { IntentDataCapture() }
        single<RawDataProcess> { AndroidDataProcess(context = appContext) }
        single<AndroidImageLoader> { GlideImageLoader() }

        // repos
        single<BookmarkRepository> {
            SharedBookmarkRepository(
                imageBookmarkDAO = (get() as Database).getImageBookmarkDAO(),
                linkBookmarkDAO = (get() as Database).getLinkBookmarkDAO(),
                textBookmarkDAO = (get() as Database).getTextBookmarkDAO()
            )
        }
        single<TopicsRepository> {
            SharedTopicsRepository(
                topicDAO = (get() as Database).getTopicDAO()
            )
        }

        // view models
        factory<PreviewViewModel> {
            SharedPreviewViewModel(
                store = get(),
                bookmarkRepository = get(),
                calendar = get(),
                processor = get())
        }

        factory<MainHubViewModel> {
            SharedMainHubViewModel(
                store = get(),
                calendar = get(),
                bookmarkRepository = get(),
                topicsRepository = get())
        }

        factory<AddTopicViewModel> {
            SharedAddTopicViewModel(
                store = get(),
                topicsRepository = get()
            )
        }

        factory<TopicsViewModel> {
            SharedTopicsViewModel(
                store = get(),
                calendar = get(),
                topicsRepository = get()
            )
        }

        factory<EditBookmarkViewModel> {
            SharedEditBookmarkViewModel(
                store = get(),
                calendar = get(),
                bookmarkRepository = get(),
                topicsRepository = get()
            )
        }

        // adapters
        factory { BookmarksAdapter(imageLoader = get()) }
        factory { PreviewsAdapter(imageLoader = get()) }
        factory { TopicsAdapter() }
        factory { EditBookmarksAdapter() }
    }
}