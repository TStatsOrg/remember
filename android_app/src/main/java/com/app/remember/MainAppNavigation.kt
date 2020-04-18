package com.app.remember

import android.content.Context
import android.content.Intent
import com.app.feature.hub.MainHubActivity
import com.app.shared.navigation.AppNavigation

class MainAppNavigation: AppNavigation {
    override fun seeMainHub(context: Context) {
        val intent = Intent(context, MainHubActivity::class.java)
        context.startActivity(intent)
    }
}