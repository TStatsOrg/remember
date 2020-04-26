package com.app.feature.bookmark.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.feature.bookmark.edit.databinding.ViewEditBookmarkBinding
import com.app.feature.bookmark.edit.viewholders.SelectTopicViewHolder
import com.app.shared.feature.editbookmark.EditBookmarkViewModel
import com.app.shared.navigation.AppNavigation
import org.koin.android.ext.android.inject

class EditBookmarkActivity: AppCompatActivity() {

    private val navigator: AppNavigation by inject()
    private val viewModel: EditBookmarkViewModel by inject()
    private val adapter: EditBookmarksAdapter by inject()
    private val layoutManager = LinearLayoutManager(this)
    private val animator = DefaultItemAnimator()

    private val binding by lazy {
        ViewEditBookmarkBinding.inflate(layoutInflater)
    }

    private val bookmarkId by lazy {
        intent.getIntExtra(AppNavigation.BOOKMARK_ID, -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.observeEditBookmarkState {
            redraw(viewState = EditBookmarkViewState(state = it))
        }

        viewModel.observeBookmarkSaved {
            finish()
        }

        binding.saveBookmark.setOnClickListener {
            viewModel.save()
        }

        binding.topicsRecyclerView.adapter = adapter
        binding.topicsRecyclerView.layoutManager = layoutManager
        binding.topicsRecyclerView.itemAnimator = animator

        viewModel.loadEditableBookmark(forId = bookmarkId)
    }

    private fun redraw(viewState: EditBookmarkViewState) {
        adapter.redraw(viewState = viewState.topicsViewState)

        adapter.listener = object : SelectTopicViewHolder.Listener {
            override fun onClickTopic(id: Int) {
                viewModel.update(bookmark = viewState.bookmarkId, withTopic = id)
            }
        }
    }
}