package com.app.views.views

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.app.views.R
import com.google.android.material.bottomsheet.BottomSheetDialog

object BottomDialog {

    fun create(context: Context): BottomSheetDialog {
        return BottomSheetDialog(context)//, R.style.SheetDialog)
    }
}

fun <T: ViewBinding> BottomSheetDialog.show(binding: T, callback: (T, BottomSheetDialog) -> Unit) {
    setContentView(binding.root)
    callback(binding, this)
    show()
}