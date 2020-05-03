package com.app.feature.hub

import android.app.SearchManager
import android.database.Cursor
import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dependencies.navigation.Navigation
import com.app.feature.hub.adapters.BookmarksAdapter
import com.app.feature.hub.adapters.SuggestionAdapter
import com.app.feature.hub.databinding.ViewMainhubBinding
import com.app.feature.hub.viewholders.BookmarkViewHolder
import com.app.feature.hub.viewstates.BookmarksViewState
import com.app.feature.hub.viewstates.SuggestionsViewState
import com.app.shared.feature.mainhub.MainHubViewModel
import com.app.shared.feature.topics.TopicsViewModel
import com.app.shared.utils.MLogger
import com.app.views.viewstate.BookmarkViewState
import org.koin.android.ext.android.inject

class MainHubActivity: AppCompatActivity() {

    private val viewModel: MainHubViewModel by inject()
    private val suggestionViewModel: TopicsViewModel by inject()
    private val adapter: BookmarksAdapter by inject()
    private val navigation: Navigation by inject()
    private val layoutManager = LinearLayoutManager(this)
    private val animator = DefaultItemAnimator()
    private val suggestionsAdapter by lazy { SuggestionAdapter(context = this) }

    private val binding by lazy {
        ViewMainhubBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bookmarksRecyclerView.adapter = adapter
        binding.bookmarksRecyclerView.layoutManager = layoutManager
        binding.bookmarksRecyclerView.itemAnimator = animator

        binding.searchInput.suggestionsAdapter = suggestionsAdapter
        binding.searchInput.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 0

        binding.searchInput.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return true
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                MLogger.log("GABBOX Selected topic $selection")
                return true
            }
        })

        binding.searchInput.setOnSearchClickListener {
            suggestionViewModel.loadTopics()
        }

        binding.searchInput.setOnCloseListener {
            MLogger.log("GABBOX Close Search")
            return@setOnCloseListener false
        }

        binding.searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                MLogger.log("GABBOX Submitted query is $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MLogger.log("GABBOX New text $newText")
                return true
            }
        })

        adapter.listener = object : BookmarkViewHolder.Listener {

            override fun onTopicClick(viewState: BookmarkViewState) {
                navigation.seeEditBookmark(
                    context = this@MainHubActivity,
                    forEditingBookmark = viewState.id
                )
            }

            override fun onLongClick(viewState: BookmarkViewState) {
//                startActionMode(object : ActionMode.Callback {
//                    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
//                        return false
//                    }
//
//                    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//                        val inflater = mode?.menuInflater
//                        inflater?.inflate(R.menu.app_bar_edit_hub, menu)
//                        return true
//                    }
//
//                    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false
//
//                    override fun onDestroyActionMode(mode: ActionMode?) {
//
//                    }
//                })
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

        suggestionViewModel.observeTopicState {
            suggestionsAdapter.redraw(viewState = SuggestionsViewState(state = it))
        }

        viewModel.observeBookmarkState {
            adapter.redraw(viewState = BookmarksViewState(state = it))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadBookmarks()
    }
}