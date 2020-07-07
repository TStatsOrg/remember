package com.app.shared.data.dao

import com.app.shared.data.dto.BookmarkDTO
import com.app.shared.data.dto.TopicDTO

class DefaultRSSFeedBookmarkDAO: RSSFeedBookmarkDAO {

    private val defaultRSSFeeds = listOf(
        DefaultRSSFeedBookmarkDTO(
            id = "http://feeds.feedburner.com/TechCrunch/".hashCode(),
            title = "Tech Crunch",
            url = "http://feeds.feedburner.com/TechCrunch/",
            icon = "http://www.techcrunch.com/wp-content/themes/techcrunchmu/images/techcrunch_logo.png"
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "https://www.theguardian.com/uk/rss".hashCode(),
            title = "The Guardian UK",
            url = "https://www.theguardian.com/uk/rss",
            icon = "https://assets.guim.co.uk/images/guardian-logo-rss.c45beb1bafa34b347ac333af2e6fe23f.png"
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "http://feeds.bbci.co.uk/news/world/rss.xml".hashCode(),
            title = "BBC News - World",
            url = "http://feeds.bbci.co.uk/news/world/rss.xml",
            icon = "https://news.bbcimg.co.uk/nol/shared/img/bbc_news_120x60.gif"
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "http://feeds.washingtonpost.com/rss/rss_blogpost".hashCode(),
            title = "Washington Post",
            url = "http://feeds.washingtonpost.com/rss/rss_blogpost",
            icon = null
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "https://zoso.ro/feed/".hashCode(),
            title = "Zoso.ro",
            url = "https://zoso.ro/feed/",
            icon = null
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "http://xkcd.com/atom.xml".hashCode(),
            title = "xkcd.com",
            url = "http://xkcd.com/atom.xml",
            icon = null
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "http://feeds.feedburner.com/Explosm".hashCode(),
            title = "Exlosm.net",
            url = "http://feeds.feedburner.com/Explosm",
            icon = null
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "http://www.theverge.com/rss/index.xml".hashCode(),
            title = "The Verge",
            url = "http://www.theverge.com/rss/index.xml",
            icon = "https://cdn.vox-cdn.com/community_logos/52801/VER_Logomark_32x32..png"
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "http://www.vox.com/rss/index.xml".hashCode(),
            title = "VOX",
            url = "http://www.vox.com/rss/index.xml",
            icon = "https://cdn.vox-cdn.com/community_logos/52517/voxv.png"
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "http://feeds.wired.com/wired/index".hashCode(),
            title = "Wired",
            url = "http://feeds.wired.com/wired/index",
            icon = null
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "http://feeds.feedburner.com/oatmealfeed".hashCode(),
            title = "Oatmeal",
            url = "http://feeds.feedburner.com/oatmealfeed",
            icon = null
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "https://news.ycombinator.com/rss".hashCode(),
            title = "Hacker News",
            url = "https://news.ycombinator.com/rss",
            icon = null
        ),
        DefaultRSSFeedBookmarkDTO(
            id = "https://utopiabalcanica.net/feed/".hashCode(),
            title = "Utopia Balcanica",
            url = "https://utopiabalcanica.net/feed/",
            icon = null
        )
    )

    override fun insert(dto: BookmarkDTO.RSSFeedBookmarkDTO) = Unit

    override fun getAll(): List<BookmarkDTO.RSSFeedBookmarkDTO> = defaultRSSFeeds

    override fun delete(bookmarkId: Int) = Unit

    override fun replaceTopic(topicId: Int, withTopicDTO: TopicDTO) = Unit

    private data class DefaultRSSFeedBookmarkDTO(
        override val id: Int,
        override val date: Long = 0L,
        override val topic: TopicDTO? = null,
        override val url: String,
        override val isSubscribed: Boolean = false,
        override val title: String?,
        override val caption: String? = null,
        override val icon: String?
    ) : BookmarkDTO.RSSFeedBookmarkDTO
}