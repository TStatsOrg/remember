package com.app.shared.data.dao

import com.app.shared.data.dto.RSSDTO

class DefaultRSSDAO: RSSDAO {

    private val defaultRSSFeeds = listOf(
        DefaultRSS(
            id = "Tech Crunch".hashCode(),
            title = "Tech Crunch",
            link = "http://feeds.feedburner.com/TechCrunch/"
        ),
        DefaultRSS(
            id = "The Guardian UK".hashCode(),
            title = "The Guardian UK",
            link = "https://www.theguardian.com/uk/rss"
        ),
        DefaultRSS(
            id = "BBC News - World".hashCode(),
            title = "BBC News - World",
            link = "http://feeds.bbci.co.uk/news/world/rss.xml"
        ),
        DefaultRSS(
            id = "Washington Post".hashCode(),
            title = "Washington Post",
            link = "http://feeds.washingtonpost.com/rss/rss_blogpost"
        ),
        DefaultRSS(
            id = "Zoso.ro".hashCode(),
            title = "Zoso.ro",
            link = "https://zoso.ro/feed/"
        )
    )

    override fun getAll(): List<RSSDTO> = defaultRSSFeeds

    override fun get(rssId: Int): RSSDTO? = defaultRSSFeeds.firstOrNull { it.id == rssId }

    private data class DefaultRSS(
        override val id: Int,
        override val title: String,
        override val link: String,
        override val description: String? = null,
        override val isSubscribed: Boolean = false
    ) : RSSDTO
}