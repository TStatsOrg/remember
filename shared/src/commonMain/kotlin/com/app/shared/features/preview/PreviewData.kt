package com.app.shared.features.preview

expect class PreviewData {
    fun unbox(): PreviewDataType
}

sealed class PreviewDataType {
    data class Text(val content: String): PreviewDataType()
    data class Link(val url: String): PreviewDataType()
    object Unsupported: PreviewDataType()
}