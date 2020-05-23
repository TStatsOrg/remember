package com.app.shared.business

import com.app.shared.redux.Store
import com.app.shared.redux.store

fun Store<MainState>.topics() = store.state.topics.topics