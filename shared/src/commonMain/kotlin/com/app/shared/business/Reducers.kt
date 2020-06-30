package com.app.shared.business

import com.app.shared.data.dto.TopicDTO
import com.app.shared.redux.Reducer
import com.app.shared.utils.*

val AppStateReducer: Reducer<MainState> = { old, action ->
    when (action) {
        // bookmark/preview
        is Actions.Bookmark.Preview.Start -> old.copy(preview = PreviewState(isLoading = true))
        is Actions.Bookmark.Preview.Present -> old.copy(preview = old.preview.copy(isLoading = false, preview = action.dto.toState()))
        // bookmark/save
        is Actions.Bookmark.Add -> {
            val newBookmark = action.dto.toState()
            val newBookmarks = listOf(newBookmark) + old.bookmarks.bookmarks
            val allNewBookmarks = listOf(newBookmark) + old.allBookmarks
            old.copy(
                allBookmarks = allNewBookmarks,
                bookmarks = old.bookmarks.copy(bookmarks = newBookmarks),
                display = old.display.copy(isBookmarked = newBookmark.id == old.display.id))
        }
        // bookmark/present
        is Actions.Bookmark.Load.Start -> old.copy(bookmarks = BookmarksState(date = action.time))
        is Actions.Bookmark.Load.Success -> old.copy(
                allBookmarks = action.bookmarks.toBookmarkState(),
                bookmarks = BookmarksState(date = action.time, bookmarks = action.bookmarks.toBookmarkState())
            )
        is Actions.Bookmark.Load.Error -> old.copy(bookmarks = BookmarksState(date = action.time, error = action.error))
        // bookmark/filter
        is Actions.Bookmark.Filter -> {
            val filteredBookmarks = old.allBookmarks.filter { it.topic?.id == action.topic.id }
            old.copy(bookmarks = BookmarksState(
                filterByTopic = action.topic,
                bookmarks = filteredBookmarks,
                searchTerm = null))
        }
        // bookmark/search
        is Actions.Bookmark.Search -> {
            val searchedBookmarks = old.allBookmarks.filter {
                when (it) {
                    is BookmarkState.Text -> it.text.contains(action.term, ignoreCase = true)
                    is BookmarkState.Image -> it.topic?.name?.contains(action.term, ignoreCase = true) ?: false
                    is BookmarkState.Link -> it.title?.contains(action.term, ignoreCase = true) ?: false
                    else -> false
                }
            }

            old.copy(bookmarks = old.bookmarks.copy(
                searchTerm = action.term,
                bookmarks = searchedBookmarks,
                filterByTopic = null))
        }
        // bookmark/update
        is Actions.Bookmark.Update -> {

            val func: (BookmarkState) -> BookmarkState = { if (it.id == action.state.id) action.state else it }

            val newBookmarks = old.bookmarks.bookmarks.map(func)
            val newAllBookmarks = old.allBookmarks.map(func)

            old.copy(
                allBookmarks = newAllBookmarks,
                bookmarks = old.bookmarks.copy(bookmarks = newBookmarks),
                editBookmark = old.editBookmark?.copy(bookmark = action.state))
        }
        // bookmark/edit
        is Actions.Bookmark.Edit -> {
            val selectedBookmarkFromExisting = old.bookmarks.bookmarks.firstOrNull { it.id == action.bookmarkId }
            val selectedBookmarkFromPreview = old.preview.preview
            val selectedBookmark = selectedBookmarkFromExisting ?: selectedBookmarkFromPreview
            val allTopics = old.topics.topics
            val newEditBookmarkState = selectedBookmark?.let {
                EditBookmarkState(bookmark = it, topics = allTopics)
            }

            old.copy(editBookmark = newEditBookmarkState)
        }
        // bookmark/delete
        is Actions.Bookmark.Delete -> {
            val newBookmarks = old.bookmarks.bookmarks.filter { it.id != action.bookmarkId }
            val allNewBookmarks = old.allBookmarks.filter { it.id != action.bookmarkId }
            old.copy(
                allBookmarks = allNewBookmarks,
                bookmarks = old.bookmarks.copy(bookmarks = newBookmarks),
                display = old.display.copy(isBookmarked = false)
            )
        }
        // topics/present
        is Actions.Topics.Load.Start -> old.copy(topics = TopicsState(date = action.time, isLoading = true))
        is Actions.Topics.Load.Success -> old.copy(topics = TopicsState(date = action.time, topics = action.topics.toTopicState()))
        is Actions.Topics.Load.Error -> old.copy(topics = TopicsState(date = action.time, error = action.error))
        // topics/add
        is Actions.Topics.Add -> {
            val existingTopics = old.topics.topics
            val newTopic = listOf(action.topic.toState())
            val newTopics = (newTopic + existingTopics).distinctBy { it.id }

            old.copy(topics = TopicsState(topics = newTopics),
                editBookmark = old.editBookmark?.copy(topics = newTopics))
        }
        // topics/delete
        is Actions.Topics.Delete -> {
            val bookmarkFunc: (BookmarkState) -> BookmarkState = {
                if (it.topic?.id == action.topicId) {
                    it.copy(withTopic = TopicDTO.GeneralTopic().toState())
                } else {
                    it
                }
            }
            val newTopics = old.topics.topics.filter { it.id != action.topicId }
            val newEditableTopics = old.editBookmark?.topics?.filter { it.id != action.topicId } ?: listOf()
            val newBookmarks = old.bookmarks.bookmarks.map(bookmarkFunc)
            val allNewBookmarks = old.allBookmarks.map(bookmarkFunc)

            val newEditableBookmark = old.editBookmark?.copy(
                topics = newEditableTopics,
                bookmark = bookmarkFunc(old.editBookmark.bookmark)
            )

            old.copy(
                allBookmarks = allNewBookmarks,
                bookmarks = old.bookmarks.copy(bookmarks = newBookmarks),
                topics = old.topics.copy(topics = newTopics),
                editBookmark = newEditableBookmark
            )
        }
        // topics/edit
        is Actions.Topics.Edit -> {
            val topicToEdit = old.topics.topics.firstOrNull { it.id == action.topicId }
            topicToEdit?.let { old.copy(editTopic = EditTopicState(topic = it)) } ?: old
        }
        // topics/update
        is Actions.Topics.Update -> {
            val copyFunc: (TopicState) -> TopicState = {
                if (it.id == action.topicId) {
                    it.copy(name = action.newName)
                } else {
                    it
                }
            }

            val newTopics = old.topics.topics.map(copyFunc)
            val newSelected = old.editBookmark?.topics?.map(copyFunc) ?: listOf()
            val selectedTopic = newTopics.firstOrNull { it.id == action.topicId }

            val bookmarkFunc: (BookmarkState) -> BookmarkState = {
                if (it.topic?.id == action.topicId) {
                    it.copy(withTopic = selectedTopic)
                } else {
                    it
                }
            }

            val newBookmarks = old.bookmarks.bookmarks.map(bookmarkFunc)
            val allNewBookmarks = old.allBookmarks.map(bookmarkFunc)

            val newEditableBookmark = old.editBookmark?.copy(
                topics = newSelected,
                bookmark = bookmarkFunc(old.editBookmark.bookmark)
            )

            old.copy(
                allBookmarks = allNewBookmarks,
                bookmarks = old.bookmarks.copy(bookmarks = newBookmarks),
                topics = old.topics.copy(topics = newTopics),
                editBookmark = newEditableBookmark
            )
        }
        // rss/load
        is Actions.RSS.Load.Start -> old.copy(allRssFeeds = RSSState())
        is Actions.RSS.Load.Success -> old.copy(allRssFeeds = RSSState(feed = action.rss.toRSSState(), time = action.time))
        is Actions.RSS.Load.Error -> old.copy(allRssFeeds = RSSState(error = action.error, time = action.time))
        // rss/user/load
        is Actions.RSS.User.Load -> old.copy(userRssFeeds = RSSState(feed = action.rss.toRSSState(), time = action.time))
        // rss/detail
        is Actions.RSS.Detail.Present -> old.copy(rssFeedDetail = RSSFeedDetailState(feedState = action.rss.toState()))
        is Actions.RSS.Detail.LoadItems.Start -> old.copy(rssFeedDetail = old.rssFeedDetail.copy(items = listOf(), error = null))
        is Actions.RSS.Detail.LoadItems.Success -> old.copy(rssFeedDetail = old.rssFeedDetail.copy(items = action.items.toRSSItemState(), error = null))
        is Actions.RSS.Detail.LoadItems.Error -> old.copy(rssFeedDetail = old.rssFeedDetail.copy(error = action.error, items = listOf()))
        // rss/subscribe
        is Actions.RSS.Subscribe -> {

            val feeds = old.allRssFeeds.feed.map {
                if (it.id == action.id) {
                    it.copy(isSubscribed = true)
                } else {
                    it
                }
            }

            val oldFeedDetailState = old.rssFeedDetail.feedState
            val newRssFeedDetail = if (oldFeedDetailState.id == action.id) oldFeedDetailState.copy(isSubscribed = true) else oldFeedDetailState
            val allUserFeeds = feeds.filter { it.isSubscribed }

            old.copy(
                allRssFeeds = old.allRssFeeds.copy(feed = feeds),
                userRssFeeds = old.userRssFeeds.copy(feed = allUserFeeds),
                rssFeedDetail = old.rssFeedDetail.copy(feedState = newRssFeedDetail)
            )
        }
        is Actions.RSS.Unsubscribe -> {

            val feeds = old.allRssFeeds.feed.map {
                if (it.id == action.id) {
                    it.copy(isSubscribed = false)
                } else {
                    it
                }
            }

            val oldFeedDetailState = old.rssFeedDetail.feedState
            val newRssFeedDetail = if (oldFeedDetailState.id == action.id) oldFeedDetailState.copy(isSubscribed = false) else oldFeedDetailState
            val allUserFeeds = feeds.filter { it.isSubscribed }

            old.copy(
                allRssFeeds = old.allRssFeeds.copy(feed = feeds),
                userRssFeeds = old.userRssFeeds.copy(feed = allUserFeeds),
                rssFeedDetail = old.rssFeedDetail.copy(feedState = newRssFeedDetail)
            )
        }
        // display
        is Actions.Display.Show -> {
            // get item from current RSSs
            val rssItem = old.rssFeedDetail.items.firstOrNull { it.id == action.id }
            val bookmarkItem = old.allBookmarks.firstOrNull { it.id == action.id }

            val display1 = rssItem?.let {
                DisplayState(
                    id = it.id,
                    title = it.title,
                    url = it.link,
                    isBookmarked = false,
                    date = it.pubDate
                )
            }

            val display2 = when (bookmarkItem) {
                is BookmarkState.Link -> DisplayState(
                    id = bookmarkItem.id,
                    title = bookmarkItem.title ?: "",
                    url = bookmarkItem.url,
                    isBookmarked = true,
                    date = bookmarkItem.date,
                    caption = bookmarkItem.caption
                )
                else -> null
            }

            val newState = display2 ?: display1 ?: old.display

            old.copy(display = newState)
        }
        else -> old
    }
}