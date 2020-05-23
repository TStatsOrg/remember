package com.app.views.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri

interface Navigation {

    companion object {
        const val BOOKMARK_ID = "bookmark_id"
        const val TOPIC_ID = "topic_id"

        fun getTopicId(intent: () -> Intent): Lazy<Int> {
            return lazy { intent().getIntExtra(TOPIC_ID, -1) }
        }

        fun getBookmarkId(intent: () -> Intent): Lazy<Int> {
            return lazy { intent().getIntExtra(BOOKMARK_ID, -1) }
        }
    }

    fun seeMainHub(context: Context)
    fun seeTopicsList(context: Context)
    fun seeEditBookmark(context: Context, forEditingBookmark: Int)
    fun seeAddTopic(context: Context)
    fun seeEditTopic(context: Context, forEditingTopic: Int)
    fun seeUrlDestination(context: Context, uri: Uri?)
}