package com.app.dependencies.navigation

import android.content.Context
import android.net.Uri

interface Navigation {

    companion object {
        const val BOOKMARK_ID = "bookmark_id"
    }

    fun seeMainHub(context: Context)
    fun seeTopicsList(context: Context)
    fun seeEditBookmark(context: Context, forEditingBookmark: Int)
    fun seeAddTopic(context: Context)
    fun seeUrlDestination(context: Context, uri: Uri?)
}