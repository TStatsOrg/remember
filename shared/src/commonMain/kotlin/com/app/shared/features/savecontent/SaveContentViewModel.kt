package com.app.shared.features.savecontent

import com.app.shared.business.Bookmark

interface SaveContentViewModel {

    fun clear()
    fun handle(sharedData: SharedData)
    fun save()
    fun observePreviewState(callback: (Bookmark) -> Unit)
}