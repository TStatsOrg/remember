package com.app.feature.hub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.feature.hub.databinding.ViewMainhubBinding
import com.app.shared.feature.mainhub.MainHubViewModel
import com.app.shared.utils.MLogger
import org.koin.android.ext.android.inject

class MainHubActivity: AppCompatActivity() {

    private val viewModel: MainHubViewModel by inject()
    private val adapter: BookmarksAdapter = BookmarksAdapter()

    private val binding by lazy {
        ViewMainhubBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bookmarksRecyclerView.adapter = adapter
        binding.bookmarksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookmarksRecyclerView.itemAnimator = DefaultItemAnimator()

        viewModel.observeBookmarkState {
            MLogger.log("Got ${it.size} elements")
            adapter.redraw(viewState = it.map { BookmarkViewState(bookmark = it) })
        }

        viewModel.loadBookmarks()
    }
}