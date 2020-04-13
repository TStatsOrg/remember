package com.app.remember

import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.app.feature.hub.BookmarksAdapter
import com.app.remember.tmpdependencies.AppDatabase
import com.app.remember.tmpdependencies.toAbstract
import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.data.capture.DataCapture
import com.app.shared.data.capture.DataProcess
import com.app.shared.data.capture.SystemDataCapture
import com.app.shared.data.capture.SystemDataProcess
import com.app.shared.data.repository.BookmarkRepository
import com.app.shared.data.repository.SystemBookmarkRepository
import com.app.shared.feature.capture.DataCaptureViewModel
import com.app.shared.feature.capture.SharedDataCaptureViewModel
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
        single {
            Store(initialState = AppState(), reducer = AppStateReducer)
        }
        single<CalendarUtils> {
            SystemCalendarUtils()

        }

        single<BookmarkRepository> {
            SystemBookmarkRepository(
                imageBookmarkDAO = db.imageDao().toAbstract(),
                linkBookmarkDAO = db.linkDao().toAbstract(),
                textBookmarkDAO = db.textDao().toAbstract()
            )
        }

        single<PreviewViewModel> {
            SharedPreviewViewModel(
                store = get(),
                bookmarkRepository = get(),
                calendarUtils = get())
        }

        single<MainHubViewModel> {
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

//        factory<DataCapture> { params ->
//            SystemDataCapture(intent = params.get(0) as Intent)
//        }
//
//        single<DataProcess> {
//            SystemDataProcess()
//        }

        factory<DataCaptureViewModel> { params ->
            SharedDataCaptureViewModel(
                dataCapture = SystemDataCapture(intent = params.get(0) as Intent),
                dataProcess = SystemDataProcess()
            )
        }
    }
}