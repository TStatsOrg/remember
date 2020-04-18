package com.app.feature.preview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.preview.databinding.ViewPreviewBinding
import com.app.shared.data.capture.RawDataCapture
import com.app.shared.feature.preview.PreviewViewModel
import com.app.shared.navigation.AppNavigation
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

        viewModel.observePreviewState {
            redraw(viewState = PreviewViewState(content = it))
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