package com.app.shared.features.savecontent

import android.content.Intent

actual class SharedSaveContentViewModel: SaveContentViewModel {

    fun handleContent(fromIntent: Intent) {
        if (fromIntent.isSendAction()) {

            if (fromIntent.isOfMimeType(SaveContentViewModel.HandledContent.Text)) {
                // handle text
            }

            if (fromIntent.isOfMimeType(SaveContentViewModel.HandledContent.Image)) {
                // handle image
            }
        }
    }

    private fun Intent.isSendAction(): Boolean {
        return this.action == Intent.ACTION_SEND
    }

    private fun Intent.isOfMimeType(type: SaveContentViewModel.HandledContent): Boolean {
        return this.type?.contains(type.mimeType) ?: false
    }
}