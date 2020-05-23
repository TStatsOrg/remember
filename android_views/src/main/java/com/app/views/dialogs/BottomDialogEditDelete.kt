package com.app.views.dialogs

import android.app.Activity
import com.app.views.databinding.DialogEditBookmarkBinding

fun BottomDialogEditDelete(
    context: Activity,
    editAction: (() -> Unit)? = null,
    deleteAction: (() -> Unit)? = null
) {
    BottomDialog.create(context)
        .show(DialogEditBookmarkBinding.inflate(context.layoutInflater)) { binding, dialog ->
            binding.editButton.setOnClickListener {
                editAction?.invoke()
                dialog.cancel()
            }
            binding.deleteButton.setOnClickListener {
                deleteAction?.invoke()
                dialog.cancel()
            }
            binding.cancelButton.setOnClickListener {
                dialog.cancel()
            }
        }
}