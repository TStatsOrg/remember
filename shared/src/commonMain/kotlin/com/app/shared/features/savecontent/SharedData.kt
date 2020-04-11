package com.app.shared.features.savecontent

expect class SharedData {
    fun unbox(): SharedDataType
}

sealed class SharedDataType {
    data class Text(val content: String): SharedDataType()
    data class Link(val url: String): SharedDataType()
    object Unsupported: SharedDataType()
}