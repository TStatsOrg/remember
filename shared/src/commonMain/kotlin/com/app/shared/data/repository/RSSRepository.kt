package com.app.shared.data.repository

import com.app.shared.business.Either
import com.app.shared.data.dto.RSSDTO
import com.app.shared.data.dto.RSSItemDTO

interface RSSRepository {
    suspend fun getAll(): List<RSSDTO>
    suspend fun get(rssId: Int): RSSDTO?
    suspend fun getAllItems(dto: RSSDTO): Either<List<RSSItemDTO>>
}