package com.app.feature.preview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.preview.databinding.ViewPreviewBinding
import com.app.shared.feature.preview.PreviewViewModel
import com.app.shared.feature.preview.PreviewData
import com.app.shared.navigation.AppNavigation
import org.koin.android.ext.android.inject

class PreviewActivity: AppCompatActivity(), PreviewViewModel.Delegate {

    private val binding: ViewPreviewBinding by lazy {
        ViewPreviewBinding.inflate(layoutInflater)
    }

    private val viewModel: PreviewViewModel by inject()
    private val navigator: AppNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.delegate = this

        viewModel.observePreviewState {
            redraw(viewState = PreviewViewState(content = it))
        }

        val data = PreviewData(intent = intent)
        viewModel.handle(previewData = data)

        binding.saveContentButton.setOnClickListener {
            viewModel.save(previewData = data)
        }
    }

    private fun redraw(viewState: PreviewViewState) = with(viewState) {
        binding.textContent.text = resource
    }

    // PreviewViewModel.Delegate

    override fun didSaveBookmark() = navigator.seeMainHub(context = this)
}