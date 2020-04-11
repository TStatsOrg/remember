package com.app.shared.features.savecontent

import com.app.shared.business.SavedContent

interface SaveContentViewModel {

    fun clear()
    fun handle(sharedData: SharedData)
    fun observePreviewState(callback: (SavedContent) -> Unit)
}