package com.app.shared

import com.app.shared.coroutines.DispatcherFactory
import com.app.shared.coroutines.TestDispatcher
import kotlin.test.BeforeTest

abstract class DefaultTest {

    @BeforeTest
    fun `setup test`() {
        DispatcherFactory.setMain(dispatcher = TestDispatcher)
        DispatcherFactory.setIO(dispatcher = TestDispatcher)
        DispatcherFactory.setDefault(dispatcher = TestDispatcher)
    }
}