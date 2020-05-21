package com.app.feature.hub

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dependencies.navigation.Navigation
import com.app.feature.hub.adapters.BookmarksAdapter
import com.app.feature.hub.databinding.ViewMainhubBinding
import com.app.feature.hub.viewholders.BookmarkViewHolder
import com.app.feature.hub.viewstates.BookmarksViewState
import com.app.shared.feature.mainhub.MainHubViewModel
import com.app.views.viewstate.BookmarkViewState
import org.koin.android.ext.android.inject

class MainHubActivity: AppCompatActivity() {

    private val viewModel: MainHubViewModel by inject()
    private val adapter: BookmarksAdapter by inject()
    private val navigation: Navigation by inject()
    private val layoutManager = LinearLayoutManager(this)
    private val animator = DefaultItemAnimator()

    private val binding by lazy {
        ViewMainhubBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bookmarksRecyclerView.adapter = adapter
        binding.bookmarksRecyclerView.layoutManager = layoutManager
        binding.bookmarksRecyclerView.itemAnimator = animator

        binding.searchInput.observeSearchChanged {
            viewModel.search(byName = it)
        }

        binding.searchInput.observeSearchSubmitted {
            viewModel.search(byName = it)
        }

        binding.searchInput.observeSearchClosed {
            viewModel.loadBookmarks()
        }

        adapter.listener = object : BookmarkViewHolder.Listener {

            override fun onLinkClick(url: Uri?) {
                navigation.seeUrlDestination(context = this@MainHubActivity, uri = url)
            }

            override fun onLongClick(viewState: BookmarkViewState) {
                BottomDialogEditBookmark(
                    context = this@MainHubActivity,
                    editAction = {
                        navigation.seeEditBookmark(context = this@MainHubActivity, forEditingBookmark = viewState.id)
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
                else -> false
            }
        }

        viewModel.observeBookmarkState {
            val viewState = BookmarksViewState(state = it)
            adapter.redraw(viewState = viewState)
            binding.noResultsView.redraw(viewState = viewState)
            binding.searchInput.queryHint = viewState.searchText
        }

        viewModel.loadBookmarks()
    }

    override fun onDestroy() {
        viewModel.cleanup()
        binding.searchInput.cleanup()
        super.onDestroy()
    }
}