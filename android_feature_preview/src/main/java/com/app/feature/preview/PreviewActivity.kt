package com.app.feature.preview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.preview.databinding.ViewPreviewBinding
import com.app.shared.coroutines.AppScope
import com.app.shared.coroutines.DefaultDispatcher
import com.app.shared.data.capture.AndroidDataProcess
import com.app.shared.data.capture.IntentDataCapture
import com.app.shared.feature.capture.DataCaptureViewModel
import com.app.shared.feature.preview.PreviewViewModel
import com.app.shared.navigation.AppNavigation
import com.app.shared.utils.MLogger
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PreviewActivity: AppCompatActivity(), PreviewViewModel.Delegate {

    private val binding: ViewPreviewBinding by lazy {
        ViewPreviewBinding.inflate(layoutInflater)
    }

    private val viewModel: PreviewViewModel by inject()
    private val captureViewModel: DataCaptureViewModel by inject(parameters = { parametersOf(intent) })
    private val navigator: AppNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val capture = IntentDataCapture(intent = intent)
        val process = AndroidDataProcess()
        capture.capture {
            AppScope.launch(context = DefaultDispatcher) {
                val result = process.process(capture = it)
                MLogger.log("Value: $result")
            }
        }

//        viewModel.delegate = this
//
//        viewModel.observePreviewState {
//            redraw(viewState = PreviewViewState(content = it))
//        }
//
//        captureViewModel.capture { item ->
//            viewModel.present(processed = item)
//
//            binding.saveContentButton.setOnClickListener {
//                viewModel.save(processed = item)
//            }
//        }
    }

    private fun redraw(viewState: PreviewViewState) = with(viewState) {
        binding.textContent.text = "$resource"
    }

    // PreviewViewModel.Delegate

    override fun didSaveBookmark() = navigator.seeMainHub(context = this)
}