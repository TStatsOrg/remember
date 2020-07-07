package com.app.shared.data.source

import com.app.shared.business.Either
import com.app.shared.data.dto.FeedItemDTO

interface FeedItemDataSource {
    fun getItems(fromLink: String): Either<List<FeedItemDTO>>
}