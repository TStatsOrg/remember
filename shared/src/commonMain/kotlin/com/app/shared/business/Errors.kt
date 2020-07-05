package com.app.shared.business

sealed class Errors: Throwable() {
    object Network: Errors()
    object InvalidRSSFormat: Errors()
    object Database: Errors()
    object InvalidURL: Errors()
    object InvalidHTMLFormat: Errors()
}