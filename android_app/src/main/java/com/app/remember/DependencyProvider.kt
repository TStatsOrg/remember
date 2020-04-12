package com.app.remember

import android.content.Context
import androidx.room.Room
import com.app.feature.hub.BookmarksAdapter
import com.app.remember.tmpdependencies.AppDatabase
import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.data.capture.SystemDataProcess
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

    val db by lazy {
        Room.databaseBuilder(appContext, AppDatabase::class.java, "bookmark-database").build()
    }

    val module = module {
        single { Store(initialState = AppState(), reducer = AppStateReducer) }
        single<CalendarUtils> { SystemCalendarUtils() }
        single<PreviewViewModel> { SharedPreviewViewModel(store = get(), bookmarkRepository = get(), process = SystemDataProcess()) }
        single<MainHubViewModel> { SharedMainHubViewModel(store = get(), calendar = get(), bookmarkRepository = get()) }
        single<AppNavigation> { MainAppNavigation() }
        single { BookmarksAdapter() }

        single { db.toAbstract() }
        single<BookmarkRepository> { SharedBookmarkRepository(dao = get()) }
    }
}