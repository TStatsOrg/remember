package com.app.remember

import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.features.mainhub.MainHubViewModel
import com.app.shared.features.mainhub.SharedMainHubViewModel
import com.app.shared.features.savecontent.SaveContentViewModel
import com.app.shared.features.savecontent.SharedSaveContentViewModel
import com.app.shared.navigation.AppNavigation
import com.app.shared.redux.Store
import org.koin.dsl.module

object DependencyProvider {

    val module = module {
        single { Store(initialState = AppState(), reducer = AppStateReducer) }
        single<SaveContentViewModel> { SharedSaveContentViewModel(store = get()) }
        single<MainHubViewModel> { SharedMainHubViewModel(store = get()) }
        single<AppNavigation> { MainAppNavigation() }
    }
}