package com.app.feature.bookmark.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.views.navigation.Navigation
import com.app.feature.bookmark.edit.databinding.ViewEditBookmarkBinding
import com.app.feature.bookmark.edit.viewholders.SelectTopicViewHolder
import com.app.shared.feature.editbookmark.EditBookmarkViewModel
import org.koin.android.ext.android.inject

class EditBookmarkActivity: AppCompatActivity() {

    private val bookmarkId by Navigation.getBookmarkId(intent = { intent })
    private val navigator: Navigation by inject()
    private val viewModel: EditBookmarkViewModel by inject()
    private val adapter: EditBookmarksAdapter by inject()
    private val layoutManager = LinearLayoutManager(this)
    private val animator = DefaultItemAnimator()
    private val binding by lazy { ViewEditBookmarkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.observeEditBookmarkState {
            redraw(viewState = EditBookmarkViewState(state = it))
        }

        viewModel.observeBookmarkSaved {
            finish()
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.topics -> {
                    navigator.seeAddTopic(context = this)
                    true
                }
                else -> false
            }
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
                viewModel.save()
            }
        }
    }

    override fun onDestroy() {
        viewModel.cleanup()
        super.onDestroy()
    }
}