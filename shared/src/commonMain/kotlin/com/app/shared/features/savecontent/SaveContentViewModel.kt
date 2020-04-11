package com.app.shared.features.savecontent

interface SaveContentViewModel {

    enum class HandledContent(val mimeType: String) {
        Text(mimeType = "text/"),
        Image(mimeType = "image/")
    }
}