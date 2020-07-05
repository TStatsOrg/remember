package com.app.shared.data.capture

import com.app.shared.business.Either

interface HTMLDataProcess {

    fun process(html: String): Either<Result>

    data class Result(
        val title: String?,
        val caption: String?,
        val icon: String?
    )
}