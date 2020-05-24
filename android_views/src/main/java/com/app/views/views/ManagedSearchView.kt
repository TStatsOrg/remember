package com.app.views.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.app.shared.observ.InfiniteEmitter
import com.app.views.utils.hideKeyboard
import com.google.android.material.textfield.TextInputEditText

class ManagedSearchView: TextInputEditText {

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    private val searchChangedEmitter = InfiniteEmitter<String>()

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = Unit
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            text?.toString()?.let {
                searchChangedEmitter.emit(value = it)
            }
        }
    }

    init {
        addTextChangedListener(textWatcher)
        setOnTouchListener { view, motionEvent ->
            isFocusable = true
            isFocusableInTouchMode = true
            false
        }
    }

    fun updateText(text: String) {
        removeTextChangedListener(textWatcher)
        setText(text)
        addTextChangedListener(textWatcher)
    }

    fun loseFocus() {
        isFocusable = false
        context.hideKeyboard(view = this)
        updateText(text = "")
    }

    fun observeSearchChanged(callback: (String) -> Unit) = searchChangedEmitter.observe().collect(callback)

    fun cleanup() {
        searchChangedEmitter.cleanup()
    }
}