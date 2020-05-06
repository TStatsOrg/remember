package com.app.feature.hub.adapters

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.provider.BaseColumns
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import com.app.feature.hub.R
import com.app.feature.hub.viewstates.BookmarksViewState
import com.app.shared.observ.ObservableEmitter

class SuggestionAdapter(context: Context): SimpleCursorAdapter(
    context,
    R.layout.view_topic_suggestion,
    null,
    arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1),
    intArrayOf(R.id.topic_suggestion),
    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
), SearchView.OnSuggestionListener {

    private val suggestionClickedEmitter = ObservableEmitter<String>()

    fun redraw(viewState: BookmarksViewState) {
        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
        viewState.suggestionsViewState.forEachIndexed { index, s ->
            cursor.addRow(arrayOf(index, s.name))
        }
        changeCursor(cursor)
    }

    override fun onSuggestionSelect(position: Int): Boolean = true

    override fun onSuggestionClick(position: Int): Boolean {
        val cursor = getItem(position) as Cursor
        val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
        suggestionClickedEmitter.emit(value = selection)
        return true
    }

    fun observeSuggestionClicked(callback: (String) -> Unit) = suggestionClickedEmitter.observer().collect(callback)
}