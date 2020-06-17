package com.app.shared.data.dao

import com.app.shared.data.dto.RSSDTO

class DefaultRSSDAO: RSSDAO {
    override fun getAll(): List<RSSDTO> {
        return listOf(
            DefaultRSS(
                id = "xckd.com".hashCode(),
                title = "xckd.com",
                link = "https://feeder.co/add-feed?url=http://xkcd.com/atom.xml"
            ),
            DefaultRSS(
                id = "Explosm.net".hashCode(),
                title = "Explosm.net",
                link = "https://feeder.co/add-feed?url=http://feeds.feedburner.com/Explosm"
            ),
            DefaultRSS(
                id = "BBC News - World".hashCode(),
                title = "BBC News - World",
                link = "https://feeder.co/add-feed?url=http://feeds.bbci.co.uk/news/rss.xml"
            ),
            DefaultRSS(
                id = "Slashdot".hashCode(),
                title = "Slashdot",
                link = "https://feeder.co/add-feed?url=http://rss.slashdot.org/Slashdot/slashdot"
            )
        )
    }

    private data class DefaultRSS(
        override val id: Int,
        override val title: String,
        override val link: String,
        override val description: String? = null,
        override val isSubscribed: Boolean = false
    ) : RSSDTO
}