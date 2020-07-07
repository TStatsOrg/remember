package com.app.shared.data.repository

import com.app.shared.business.Either
import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.RSSDTO
import com.app.shared.data.dto.RSSItemDTO

@Deprecated(message = "Some methods in here should be removed at some point")
interface RSSRepository {
    suspend fun getAll(): List<RSSDTO>
    suspend fun getUserFeeds(): List<RSSDTO>
    suspend fun get(rssId: Int): RSSDTO?
    suspend fun getAllItems(url: String): Either<List<RSSItemDTO>>
    suspend fun subscribe(rssId: Int)
    suspend fun unsubscribe(rssId: Int)
}