package com.app.shared.data.repository

import com.app.shared.data.dto.RSSDTO

interface RSSRepository {
    suspend fun getAll(): List<RSSDTO>
    suspend fun get(rssId: Int): RSSDTO?
}