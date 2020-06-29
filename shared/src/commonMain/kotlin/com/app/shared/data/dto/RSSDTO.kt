package com.app.shared.data.dto

interface RSSDTO {
    val id: Int
    val title: String
    val link: String
    val icon: String?
    val caption: String?
    val isSubscribed: Boolean
}