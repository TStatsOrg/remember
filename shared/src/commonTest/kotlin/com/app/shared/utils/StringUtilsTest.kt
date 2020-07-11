package com.app.shared.utils

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StringUtilsTest: DefaultTest() {

    @Test
    fun `utils can split a single word sentence`() = runTest {
        // given
        val utils = StringUtils()

        // when
        val result = utils.split(sentence = "Word")

        // then
        assertEquals(listOf("Word"), result)
    }

    @Test
    fun `utils can split a multi word sentence`() = runTest {
        // given
        val utils = StringUtils()

        // when
        val result = utils.split(sentence = "I'm home, baby 小米.")

        // then
        assertEquals(listOf("I'm", "home", "baby", "小米"), result)
    }

    @Test
    fun `utils can split a website name delimited by dot`() = runTest {
        // given
        val utils = StringUtils()

        // when
        val result = utils.split(sentence = "Website.com")

        // then
        assertEquals(listOf("Website", "com"), result)
    }

    @Test
    fun `utils can split a title separated by a dash`() = runTest {
        // given
        val utils = StringUtils()

        // when
        val result = utils.split(sentence = "News - Now")

        // then
        assertEquals(listOf("News", "Now"), result)
    }
}