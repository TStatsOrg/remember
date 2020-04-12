package com.app.remember

import com.app.feature.hub.BookmarksAdapter
import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.feature.mainhub.MainHubViewModel
import com.app.shared.feature.mainhub.SharedMainHubViewModel
import com.app.shared.feature.preview.PreviewViewModel
import com.app.shared.feature.preview.SharedPreviewViewModel
import com.app.shared.navigation.AppNavigation
import com.app.shared.redux.Store
import com.app.shared.utils.CalendarUtils
import com.app.shared.utils.SystemCalendarUtils
import org.koin.dsl.module

object DependencyProvider {

    val module = module {
        single { Store(initialState = AppState(), reducer = AppStateReducer) }
        single<CalendarUtils> { SystemCalendarUtils() }
        single<PreviewViewModel> { SharedPreviewViewModel(store = get(), calendar = get()) }
        single<MainHubViewModel> { SharedMainHubViewModel(store = get(), calendar = get()) }
        single<AppNavigation> { MainAppNavigation() }
        single { BookmarksAdapter() }

    }
}