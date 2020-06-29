package com.app.shared.data.dao

import com.app.shared.data.dto.RSSDTO

class DefaultRSSDAO: RSSDAO {

    private val defaultRSSFeeds = listOf(
        DefaultRSS(
            id = "Tech Crunch".hashCode(),
            title = "Tech Crunch",
            link = "http://feeds.feedburner.com/TechCrunch/",
            icon = "http://www.techcrunch.com/wp-content/themes/techcrunchmu/images/techcrunch_logo.png"
        ),
        DefaultRSS(
            id = "The Guardian UK".hashCode(),
            title = "The Guardian UK",
            link = "https://www.theguardian.com/uk/rss",
            icon = "https://assets.guim.co.uk/images/guardian-logo-rss.c45beb1bafa34b347ac333af2e6fe23f.png"
        ),
        DefaultRSS(
            id = "BBC News - World".hashCode(),
            title = "BBC News - World",
            link = "http://feeds.bbci.co.uk/news/world/rss.xml",
            icon = "https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif"
        ),
        DefaultRSS(
            id = "Washington Post".hashCode(),
            title = "Washington Post",
            link = "http://feeds.washingtonpost.com/rss/rss_blogpost",
            icon = null
        ),
        DefaultRSS(
            id = "Zoso.ro".hashCode(),
            title = "Zoso.ro",
            link = "https://zoso.ro/feed/",
            icon = null
        )
    )

    override fun getAll(): List<RSSDTO> = defaultRSSFeeds

    override fun get(rssId: Int): RSSDTO? = defaultRSSFeeds.firstOrNull { it.id == rssId }

    override fun insert(dto: RSSDTO) = Unit

    override fun delete(rssId: Int) = Unit

    private data class DefaultRSS(
        override val id: Int,
        override val title: String,
        override val link: String,
        override val icon: String?,
        override val caption: String? = null,
        override val isSubscribed: Boolean = false
    ) : RSSDTO
}