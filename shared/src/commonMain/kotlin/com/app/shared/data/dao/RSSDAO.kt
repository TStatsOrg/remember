package com.app.shared.data.dao

import com.app.shared.data.dto.RSSDTO

interface RSSDAO {
    fun getAll(): List<RSSDTO>
    fun get(rssId: Int): RSSDTO?
    fun insert(dto: RSSDTO)
    fun delete(rssId: Int)
}