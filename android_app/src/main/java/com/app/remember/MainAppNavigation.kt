package com.app.remember

import android.content.Context
import android.content.Intent
import com.app.feature.hub.MainHubActivity
import com.app.feature.topic.add.AddTopicActivity
import com.app.feature.topics.TopicsActivity
import com.app.shared.navigation.AppNavigation
import com.app.shared.navigation.AppNavigation.Companion.BOOKMARK_ID

class MainAppNavigation: AppNavigation {

    override fun seeMainHub(context: Context) {
        val intent = Intent(context, MainHubActivity::class.java)
        context.startActivity(intent)
    }

    override fun seeTopicsList(context: Context) {
        val intent = Intent(context, TopicsActivity::class.java)
        context.startActivity(intent)
    }

    override fun seeTopicsList(context: Context, forEditingBookmark: Int) {
        val intent = Intent(context, TopicsActivity::class.java).apply {
            putExtra(BOOKMARK_ID, forEditingBookmark)
        }
        context.startActivity(intent)
    }

    override fun seeAddTopic(context: Context) {
        val intent = Intent(context, AddTopicActivity::class.java)
        context.startActivity(intent)
    }
}