package com.app.dependencies.data.utils

import android.content.Context
import com.app.dependencies.R
import com.app.shared.utils.DeviceUtils

class AndroidDeviceUtils(private val context: Context): DeviceUtils {
    override fun isTablet(): Boolean = context.resources.getBoolean(R.bool.isTablet)
    override fun isLandscape(): Boolean = context.resources.getBoolean(R.bool.isLandscape)
}