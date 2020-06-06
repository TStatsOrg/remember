package com.app.shared.data.repository

import com.app.shared.DefaultTest
import com.app.shared.coroutines.runTest
import com.app.shared.data.dao.ImageBookmarkDAO
import com.app.shared.data.dao.LinkBookmarkDAO
import com.app.shared.data.dao.TextBookmarkDAO
import com.app.shared.mocks.MockBookmarkDTO
import com.app.shared.mocks.MockTopicDTO
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedBookmarkRepositoryTest: DefaultTest() {

    private val imageBookmarkDAO = mockk<ImageBookmarkDAO>(relaxed = true)
    private val linkBookmarkDAO = mockk<LinkBookmarkDAO>(relaxed = true)
    private val textBookmarkDAO = mockk<TextBookmarkDAO>(relaxed = true)
    private val repository = SharedBookmarkRepository(imageBookmarkDAO, textBookmarkDAO, linkBookmarkDAO)

    @Test
    fun `repository can save a bookmark`() = runTest {
        // given
        val textDTO = MockBookmarkDTO.Text(id = 1, date = 1000, text = "cool", topic = null)
        val imageDTO = MockBookmarkDTO.Image(id = 2, url = "https://my.image/image.png", date = 1001, topic = null)
        val linkDTO = MockBookmarkDTO.Link(id = 3, date = 1002, topic = null, title = "title", caption = "caption", url = "https://my.link/link.html", icon = null)

        // when
        repository.save(dto = textDTO)
        repository.save(dto = imageDTO)
        repository.save(dto = linkDTO)

        // then
        coVerify {
            textBookmarkDAO.insert(dto = textDTO)
            linkBookmarkDAO.insert(dto = linkDTO)
            imageBookmarkDAO.insert(dto = imageDTO)
        }
    }

    @Test
    fun `repository can return all bookmarks in DAO sorted by date descending`() = runTest {
        // given
        val textDTO = MockBookmarkDTO.Text(id = 1, date = 1000, text = "cool", topic = null)
        val imageDTO = MockBookmarkDTO.Image(id = 2, url = "https://my.image/image.png", date = 1001, topic = null)
        val linkDTO = MockBookmarkDTO.Link(id = 3, date = 1002, topic = null, title = "title", caption = "caption", url = "https://my.link/link.html", icon = null)
        every { imageBookmarkDAO.getAll() } returns listOf(imageDTO)
        every { linkBookmarkDAO.getAll() } returns listOf(linkDTO)
        every { textBookmarkDAO.getAll() } returns listOf(textDTO)

        // when
        val result = repository.load()

        // then
        assertEquals(result, listOf(linkDTO, imageDTO, textDTO))
    }

    @Test
    fun `repository can delete a bookmark`() = runTest {
        // when
        repository.delete(bookmarkId = 5)

        // then
        coVerify(exactly = 1) {
            imageBookmarkDAO.delete(bookmarkId = 5)
            linkBookmarkDAO.delete(bookmarkId = 5)
            textBookmarkDAO.delete(bookmarkId = 5)
        }
    }

    @Test
    fun `repository can replace the topic of a bookmark`() = runTest {
        // given
        val topic = MockTopicDTO(id = 3, name = "my name")
        // when
        repository.replaceTopic(topicId = 2, withTopicDTO = topic)

        // then
        coVerify(exactly = 1) {
            imageBookmarkDAO.replaceTopic(topicId = 2, withTopicDTO = topic)
            linkBookmarkDAO.replaceTopic(topicId = 2, withTopicDTO = topic)
            textBookmarkDAO.replaceTopic(topicId = 2, withTopicDTO = topic)
        }
    }
}