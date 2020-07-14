package com.app.shared.data.source

import com.app.shared.business.Either
import com.app.shared.data.dto.FeedUpdateDTO

interface FeedDataSource {
    suspend fun getLastUpdateDate(url: String): Either<FeedUpdateDTO>
}