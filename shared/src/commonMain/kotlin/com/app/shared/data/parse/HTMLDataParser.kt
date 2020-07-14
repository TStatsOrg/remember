package com.app.shared.data.parse

import com.app.shared.business.Either

interface HTMLDataParser {

    fun process(html: String): Either<Result>

    data class Result(
        val title: String?,
        val caption: String?,
        val icon: String?
    )
}