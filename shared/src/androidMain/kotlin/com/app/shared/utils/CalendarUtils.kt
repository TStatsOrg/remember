package com.app.shared.utils

actual class SystemCalendarUtils: CalendarUtils {
    actual override fun getTime(): Long = System.currentTimeMillis()
}