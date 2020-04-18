package com.app.remember

import android.content.Context
import android.content.Intent
import com.app.dependencies.data.dao.RealmDatabase
import com.app.feature.hub.BookmarksAdapter
import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.data.capture.AndroidDataProcess
import com.app.shared.data.capture.IntentDataCapture
import com.app.shared.data.capture.RawDataCapture
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.dao.Database
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.SharedBookmarkRepository
import com.app.shared.feature.mainhub.MainHubViewModel
import com.app.shared.feature.mainhub.SharedMainHubViewModel
import com.app.shared.feature.preview.PreviewViewModel
import com.app.shared.feature.preview.SharedPreviewViewModel
import com.app.shared.navigation.AppNavigation
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.SystemCalendarUtils
import org.koin.dsl.module

class DependencyProvider(private val appContext: Context) {

    val module = module {
        // general
        single { Store(initialState = AppState(), reducer = AppStateReducer) }
        single<Database> { RealmDatabase(context = appContext) }
        single<AppNavigation> { MainAppNavigation() }

        // utils
        single<CalendarUtils> { SystemCalendarUtils() }
        single<RawDataCapture<Intent>> { IntentDataCapture() }
        single<RawDataProcess> { AndroidDataProcess() }

        // repos
        single<BookmarkRepository> {
            SharedBookmarkRepository(
                imageBookmarkDAO = (get() as Database).getImageBookmarkDAO(),
                linkBookmarkDAO = (get() as Database).getLinkBookmarkDAO(),
                textBookmarkDAO = (get() as Database).getTextBookmarkDAO()
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
                bookmarkRepository = get())
        }

        // adapters
        single { BookmarksAdapter() }
    }
}