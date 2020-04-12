package com.app.shared.utils

interface CalendarUtils {
    fun getTime(): Long
}

expect class SystemCalendarUtils: CalendarUtils {
    override fun getTime(): Long
}