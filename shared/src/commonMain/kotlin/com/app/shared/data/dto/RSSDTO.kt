package com.app.shared.data.dto

interface RSSDTO {
    val id: Int
    val title: String
    val link: String
    val description: String?
    val isSubscribed: Boolean
}