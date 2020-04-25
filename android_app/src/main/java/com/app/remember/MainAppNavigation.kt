package com.app.remember

import android.content.Context
import android.content.Intent
import com.app.feature.hub.MainHubActivity
import com.app.feature.topic.add.AddTopicActivity
import com.app.feature.topics.TopicsActivity
import com.app.shared.navigation.AppNavigation

class MainAppNavigation: AppNavigation {
    override fun seeMainHub(context: Context) {
        val intent = Intent(context, MainHubActivity::class.java)
        context.startActivity(intent)
    }

    override fun seeTopicsList(context: Context) {
        val intent = Intent(context, TopicsActivity::class.java)
        context.startActivity(intent)
    }

    override fun seeAddTopic(context: Context) {
        val intent = Intent(context, AddTopicActivity::class.java)
        context.startActivity(intent)
    }
}