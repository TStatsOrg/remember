package com.app.shared.data.dao

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import com.app.shared.mocks.MockBookmarkDTO
import com.app.shared.mocks.MockTopicDTO
import kotlin.test.Test
import kotlin.test.assertTrue

class DefaultFeedBookmarkDAOTest: DefaultTest() {

    @Test
    fun `default dao returns some feed bookmarks`() = runTest {
        // given
        val dao = DefaultFeedBookmarkDAO()

        // when
        val result = dao.getAll()

        // then
        assertTrue(result.isNotEmpty())

        // when
        dao.delete(bookmarkId = 1)
        dao.insert(dto = MockBookmarkDTO.Feed(id = 4, date = 1003, topic = null, title = "My Feed", caption = "My Feed", url = "https://my.feed/index.xml", icon = null, isFavourite = false))
        dao.replaceTopic(topicId = 1, withTopicDTO = MockTopicDTO(id = 1, name = "my topic"))
    }
}