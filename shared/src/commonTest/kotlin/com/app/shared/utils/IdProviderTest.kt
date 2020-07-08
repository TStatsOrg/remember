package com.app.shared.utils

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IdProviderTest: DefaultTest() {

    @Test
    fun `provider can extract an ID from a string`() = runTest {
        // given

        // when
        val result = IdProvider.fromString(string = "abc")

        // then
        assertEquals(96354, result)
    }
}