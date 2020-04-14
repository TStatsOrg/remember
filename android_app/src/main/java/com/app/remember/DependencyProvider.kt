package com.app.remember

import android.content.Context
import com.app.dependencies.data.dao.Database
import com.app.feature.hub.BookmarksAdapter
import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.data.capture.AndroidDataProcess
import com.app.shared.data.capture.RawDataProcess
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.SystemBookmarkRepository
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
        single {
            Store(initialState = AppState(), reducer = AppStateReducer)
        }
        single<CalendarUtils> {
            SystemCalendarUtils()
        }

        single<BookmarkRepository> {
            SystemBookmarkRepository(
                imageBookmarkDAO = Database.getImageDAO(),
                linkBookmarkDAO = Database.getLinkDAO(),
                textBookmarkDAO = Database.getTextDAO()
            )
        }

        single<RawDataProcess> {
            AndroidDataProcess()
        }

        factory <PreviewViewModel> {
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

        single<AppNavigation> {
            MainAppNavigation()
        }

        single {
            BookmarksAdapter()
        }
    }
}