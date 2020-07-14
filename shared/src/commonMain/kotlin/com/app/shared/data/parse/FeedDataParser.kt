package com.app.shared.data.parse

import com.app.shared.business.Either

interface FeedDataParser {

    fun process(feed: String): Either<Result>

    data class Result(
        val title: String?,
        val caption: String?,
        val icon: String?,
        val lastUpdate: Long
    )
}