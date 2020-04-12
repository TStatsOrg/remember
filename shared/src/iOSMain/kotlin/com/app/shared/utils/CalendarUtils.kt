package com.app.shared.utils

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual class SystemCalendarUtils: CalendarUtils {
    actual override fun getTime(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()
}