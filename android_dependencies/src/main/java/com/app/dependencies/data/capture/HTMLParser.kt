package com.app.dependencies.data.capture

interface HTMLParser {
    fun parse(url: String, result: (Result<Output>) -> Unit)

    data class Output(
        val title: String?,
        val description: String?,
        val icon: String?
    )
}