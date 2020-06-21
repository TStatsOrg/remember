package com.app.shared.data.dto

interface RSSItemDTO {
    val id: Int
    val title: String
    val link: String
    val pubDate: String
    val caption: String?
}