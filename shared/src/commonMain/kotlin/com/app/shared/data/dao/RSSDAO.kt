package com.app.shared.data.dao

import com.app.shared.data.dto.RSSDTO

@Deprecated(message = "Old dcode")
interface RSSDAO {
    fun getAll(): List<RSSDTO>
    fun get(rssId: Int): RSSDTO?
    fun insert(dto: RSSDTO)
    fun delete(rssId: Int)
}