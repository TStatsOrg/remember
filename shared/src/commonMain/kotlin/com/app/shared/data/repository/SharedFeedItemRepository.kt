package com.app.shared.data.repository

import com.app.shared.business.Either
import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.data.dto.FeedItemDTO
import com.app.shared.data.source.FeedItemDataSource
import kotlinx.coroutines.withContext

class SharedFeedItemRepository(
    private val dataSource: FeedItemDataSource
): FeedItemRepository {

    override suspend fun getAllItems(url: String): Either<List<FeedItemDTO>> {
        return withContext(context = DispatcherFactory.io()) {

            val data = dataSource.getItems(fromLink = url)

            return@withContext when (data) {
                is Either.Success -> Either.Success<List<FeedItemDTO>>(data = data.data.sortedBy { it.title })
                is Either.Failure -> Either.Failure<List<FeedItemDTO>>(error = data.error)
            }
        }
    }
}