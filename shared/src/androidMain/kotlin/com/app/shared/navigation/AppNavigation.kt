package com.app.shared.navigation

import android.content.Context

interface AppNavigation {

    companion object {
        const val BOOKMARK_ID = "bookmark_id"
    }

    fun seeMainHub(context: Context)
    fun seeTopicsList(context: Context)
    fun seeTopicsList(context: Context, forEditingBookmark: Int)
    fun seeAddTopic(context: Context)
}