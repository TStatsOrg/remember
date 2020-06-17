package com.app.feature.hub

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.feature.hub.adapters.BookmarksAdapter
import com.app.feature.hub.databinding.ViewMainhubBinding
import com.app.feature.hub.viewholders.BookmarkViewHolder
import com.app.feature.hub.viewstates.BookmarksViewState
import com.app.shared.feature.bookmarks.BookmarksViewModel
import com.app.shared.utils.DeviceUtils
import com.app.views.dialogs.BottomDialogEditDelete
import com.app.views.navigation.Navigation
import com.app.views.viewstate.BookmarkViewState
import org.koin.android.ext.android.inject

class MainHubActivity: AppCompatActivity() {

    private val viewModel: BookmarksViewModel by inject()
    private val adapter: BookmarksAdapter by inject()
    private val navigation: Navigation by inject()
    private val deviceUtils: DeviceUtils by inject()

    private val layoutManager: RecyclerView.LayoutManager by lazy {
        return@lazy if (deviceUtils.isTablet()) {
            val columns = if (deviceUtils.isLandscape()) 3 else 2
            StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL) as RecyclerView.LayoutManager
        } else {
            LinearLayoutManager(this) as RecyclerView.LayoutManager
        }
    }
    private val animator = DefaultItemAnimator()
    private val binding by lazy { ViewMainhubBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bookmarksRecyclerView.adapter = adapter
        binding.bookmarksRecyclerView.layoutManager = layoutManager
        binding.bookmarksRecyclerView.itemAnimator = animator

        binding.searchInput.observeSearchChanged {
            viewModel.search(byName = it)
        }

        adapter.listener = object : BookmarkViewHolder.Listener {

            override fun onLinkClick(url: Uri?) {
                navigation.seeUrlDestination(context = this@MainHubActivity, uri = url)
            }

            override fun onLongClick(viewState: BookmarkViewState) {
                BottomDialogEditDelete(
                    context = this@MainHubActivity,
                    editAction = {
                        navigation.seeEditBookmark(
                            context = this@MainHubActivity,
                            forEditingBookmark = viewState.id
                        )
                    },
                    deleteAction = {
                        viewModel.delete(bookmarkId = viewState.id)
                    }
                )
            }
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit_topics -> {
                    navigation.seeTopicsList(context = this)
                    true
                }
                R.id.clear_search -> {
                    viewModel.loadBookmarks()
                    binding.searchInput.loseFocus()
                    true
                }
                else -> false
            }
        }

        viewModel.observeBookmarkState {
            val viewState = BookmarksViewState(state = it)
            adapter.redraw(viewState = viewState)
            binding.noResultsView.redraw(viewState = viewState)
            binding.getStartedView.redraw(viewState = viewState)

            if (viewState.isFilteringByTopic) {
                binding.searchInput.updateText(text = viewState.topicSearchText)
            }
        }

        viewModel.loadBookmarks()
    }

    override fun onDestroy() {
        viewModel.cleanup()
        binding.searchInput.cleanup()
        super.onDestroy()
    }
}