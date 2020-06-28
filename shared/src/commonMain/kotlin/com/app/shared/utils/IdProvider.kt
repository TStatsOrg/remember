package com.app.shared.utils

object IdProvider {
    fun fromString(string: String): Int = string.hashCode()
}