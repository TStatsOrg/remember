package com.app.feature.hub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.feature.hub.databinding.ViewMainhubBinding
import com.app.shared.feature.mainhub.MainHubViewModel
import com.app.shared.navigation.AppNavigation
import org.koin.android.ext.android.inject

class MainHubActivity: AppCompatActivity() {

    private val viewModel: MainHubViewModel by inject()
    private val adapter: BookmarksAdapter by inject()
    private val navigation: AppNavigation by inject()
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
            adapter.redraw(viewState = BookmarksViewState(state = it))
        }

        viewModel.loadBookmarks()
    }
}