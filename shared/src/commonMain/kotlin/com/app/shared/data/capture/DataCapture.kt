package com.app.shared.data.capture

interface DataCapture {

    fun unbox(): Item

    sealed class Item {
        /**
         * A raw text item shared by the user
         */
        data class Text(val text: String): Item()

        /**
         * A raw image item shared by the user
         */
        data class Image(val url: String): Item()

        /**
         * An ambiguous item shared by the user
         */
        object Unknown: Item()
    }
}