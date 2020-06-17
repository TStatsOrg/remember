package com.app.shared.data.dao

interface Database {
    fun getImageBookmarkDAO(): ImageBookmarkDAO
    fun getLinkBookmarkDAO(): LinkBookmarkDAO
    fun getTextBookmarkDAO(): TextBookmarkDAO
    fun getTopicDAO(): TopicDAO
    fun getUserRSSDAO(): RSSDAO
}