package com.app.shared.data.capture

interface RawDataProcess {

    fun process(capture: String?, result: (Item) -> Unit)

    sealed class Item {
        /**
         * Just a simple processed text
         */
        data class Text(val text: String): Item()

        /**
         * A link item the user shared - with a url, title, description, icon, etc
         */
        data class Link(
            val url: String,
            val title: String?,
            val description: String?,
            val icon: String?
        ): Item()

        /**
         * A RSS Feed item the user has captured - with a feed url, title, caption, icon
         */
        data class RSSFeed(
            val url: String,
            val title: String?,
            val caption: String?,
            val icon: String?
        ): Item()

        /**
         * A process image the user has shared
         */
        data class Image(
            val url: String
        ): Item()

        /**
         * Still an unknown item the user has shared
         */
        object Unknown: Item()
    }
}