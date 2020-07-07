package com.app.shared.data.repository

import com.app.shared.business.Either
import com.app.shared.data.dto.FeedItemDTO

interface FeedItemRepository {
    suspend fun getAllItems(url: String): Either<List<FeedItemDTO>>
}