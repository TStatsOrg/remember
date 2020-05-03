package com.app.views.views

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.util.AttributeSet
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import com.app.shared.observ.ObservableEmitter
import com.app.views.R

class ManagedSearchView: SearchView {

    private val openSearchEmitter = ObservableEmitter<Boolean>()
    private val closeSearchEmitter = ObservableEmitter<Boolean>()
    private val suggestionClickedEmitter = ObservableEmitter<String>()
    private val searchChangedEmitter = ObservableEmitter<String>()

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    init {

        // set threshold at 0
        findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 0

        // gather round the old interface
        setOnSearchClickListener {
            openSearchEmitter.emit(value = true)
        }
        setOnCloseListener {
            closeSearchEmitter.emit(value = true)
            return@setOnCloseListener false
        }

        setOnSuggestionListener(object : OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean = true

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                suggestionClickedEmitter.emit(value = selection)
                return true
            }
        })

        setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchChangedEmitter.emit(value = it)
                }
                return true
            }
        })
    }

    fun observeSearchOpened(callback: (Boolean) -> Unit) = openSearchEmitter.observer().collect(callback)

    fun observeSearchClosed(callback: (Boolean) -> Unit) = closeSearchEmitter.observer().collect(callback)

    fun observeSuggestionClicked(callback: (String) -> Unit) = suggestionClickedEmitter.observer().collect(callback)

    fun observeSearchChanged(callback: (String) -> Unit) = searchChangedEmitter.observer().collect(callback)
}