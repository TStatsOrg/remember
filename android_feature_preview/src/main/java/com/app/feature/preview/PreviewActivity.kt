package com.app.feature.preview

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.feature.preview.databinding.ViewPreviewBinding
import com.app.shared.data.capture.RawDataCapture
import com.app.shared.feature.preview.PreviewViewModel
import com.app.shared.navigation.AppNavigation
import com.app.shared.utils.MLogger
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import org.koin.android.ext.android.inject

class PreviewActivity: AppCompatActivity() {

    private val binding: ViewPreviewBinding by lazy {
        ViewPreviewBinding.inflate(layoutInflater)
    }

    private val viewModel: PreviewViewModel by inject()
    private val navigator: AppNavigation by inject()
    private val capture: RawDataCapture<Intent> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            MLogger.log("I DO NOT HAVE WRITE_EXTERNAL_STORAGE")
        } else {
            MLogger.log("I HAVE WRITE_EXTERNAL_STORAGE")
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            MLogger.log("I DO NOT HAVE READ_EXTERNAL_STORAGE")
        } else {
            MLogger.log("I HAVE READ_EXTERNAL_STORAGE")
        }

        viewModel.observePreviewState {
            val viewState = PreviewViewState(content = it)
            redraw(viewState = viewState)

            val url: String? = viewState.resource as? String

            Glide.with(this).asBitmap().load(url).into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    binding.linkImage.setImageBitmap(resource)

                    val values = ContentValues(2)
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//                    values.put(MediaStore.Images.Media.DATA, url)
                    val contentUriFile: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                    MLogger.log("Content file $contentUriFile")

                    val stream = contentResolver.openOutputStream(contentUriFile!!)
                    resource.compress(Bitmap.CompressFormat.JPEG, 95, stream)
                    stream?.close()

                    Glide.with(this@PreviewActivity).asBitmap().load(contentUriFile).into(binding.linkImage)
                }
            })
        }
        viewModel.observeBookmarkSaved {
            navigator.seeMainHub(context = this)
        }

        binding.saveContentButton.setOnClickListener {
            viewModel.save()
        }

        capture.capture(input = intent) {
            viewModel.present(capturedRawData = it)
        }
    }

    private fun redraw(viewState: PreviewViewState) = with(viewState) {
        binding.textContent.text = "$resource"
    }
}