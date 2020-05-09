package com.app.feature.hub

import android.app.Activity
import com.app.feature.hub.databinding.DialogEditBookmarkBinding
import com.app.views.views.BottomDialog
import com.app.views.views.show

fun BottomDialogEditBookmark(
    context: Activity,
    editAction: (() -> Unit)? = null,
    deleteAction: (() -> Unit)? = null
) {
    BottomDialog.create(context)
        .show(DialogEditBookmarkBinding.inflate(context.layoutInflater)) { binding, dialog ->
            binding.editBookmarkButton.setOnClickListener {
                editAction?.invoke()
                dialog.cancel()
            }
            binding.deleteBookmarkButton.setOnClickListener {
                deleteAction?.invoke()
                dialog.cancel()
            }
            binding.cancelButton.setOnClickListener {
                dialog.cancel()
            }
        }
}