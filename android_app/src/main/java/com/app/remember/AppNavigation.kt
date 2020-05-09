package com.app.remember

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.app.dependencies.navigation.Navigation
import com.app.dependencies.navigation.Navigation.Companion.BOOKMARK_ID
import com.app.feature.bookmark.edit.EditBookmarkActivity
import com.app.feature.hub.MainHubActivity
import com.app.feature.topic.add.AddTopicActivity
import com.app.feature.topics.TopicsActivity


class AppNavigation: Navigation {

    override fun seeMainHub(context: Context) {
        val intent = Intent(context, MainHubActivity::class.java)
        context.startActivity(intent)
    }

    override fun seeTopicsList(context: Context) {
        val intent = Intent(context, TopicsActivity::class.java)
        context.startActivity(intent)
    }

    override fun seeEditBookmark(context: Context, forEditingBookmark: Int) {
        val intent = Intent(context, EditBookmarkActivity::class.java).apply {
            putExtra(BOOKMARK_ID, forEditingBookmark)
        }
        context.startActivity(intent)
    }

    override fun seeAddTopic(context: Context) {
        val intent = Intent(context, AddTopicActivity::class.java)
        context.startActivity(intent)
    }

    override fun seeUrlDestination(context: Context, uri: Uri) {
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(browserIntent)
    }
}