package com.app.dependencies.data.capture

interface ImageParser {
    fun parse(url: String): String?
}