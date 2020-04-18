package com.app.dependencies.data.utils

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader: AndroidImageLoader {
    override fun load(resource: Uri?, into: ImageView) {
        Glide.with(into.context).load(resource).into(into)
    }
}