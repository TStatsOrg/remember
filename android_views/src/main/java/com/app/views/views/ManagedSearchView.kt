package com.app.views.views

import android.content.Context
import android.util.AttributeSet
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import com.app.shared.observ.ObservableEmitter
import com.app.views.R
import com.app.views.utils.hideKeyboard

class ManagedSearchView: SearchView {

    private val openSearchEmitter = ObservableEmitter<Boolean>()
    private val closeSearchEmitter = ObservableEmitter<Boolean>()
    private val searchChangedEmitter = ObservableEmitter<String>()
    private val searchSubmittedEmitter = ObservableEmitter<String>()

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

        setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    context.hideKeyboard(view = this@ManagedSearchView)
                    searchSubmittedEmitter.emit(value = it)
                }
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

    fun observeSearchChanged(callback: (String) -> Unit) = searchChangedEmitter.observer().collect(callback)

    fun observeSearchSubmitted(callback: (String) -> Unit) = searchSubmittedEmitter.observer().collect(callback)
}