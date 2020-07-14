package com.app.shared.data.source

import com.app.shared.business.Either
import com.app.shared.business.Errors
import com.app.shared.data.dto.FeedUpdateDTO
import com.app.shared.data.parse.FeedDataParser
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class SharedFeedDataSource(
    private val feedDataParser: FeedDataParser
): FeedDataSource {

    private val client = HttpClient()

    override suspend fun getLastUpdateDate(url: String): Either<FeedUpdateDTO> {
        return try {
            val feedData: String = client.get(urlString = url)

            when (val result = feedDataParser.process(feed = feedData)) {
                is Either.Success -> Either.Success(data = object : FeedUpdateDTO {
                    override val id: Int = url.hashCode()
                    override val url: String = url
                    override val lastUpdate: Long = result.data.lastUpdate
                })
                is Either.Failure -> Either.Failure(error = result.error)
            }
        } catch (e: Throwable) {
            Either.Failure(error = Errors.Network)
        }
    }
}