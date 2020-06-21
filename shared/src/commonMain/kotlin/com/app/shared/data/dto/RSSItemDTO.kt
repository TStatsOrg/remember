package com.app.shared.data.dto

interface RSSItemDTO {
    val id: Int
    val title: String
    val link: String
    val pubDate: Long
    val caption: String?
}