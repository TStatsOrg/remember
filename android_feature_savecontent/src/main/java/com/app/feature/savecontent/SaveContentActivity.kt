package com.app.feature.savecontent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.savecontent.databinding.ViewSharecontentBinding
import com.app.shared.features.savecontent.SaveContentViewModel
import com.app.shared.features.savecontent.SharedData
import com.app.shared.navigation.AppNavigation
import org.koin.android.ext.android.inject

class SaveContentActivity: AppCompatActivity() {

    private val binding: ViewSharecontentBinding by lazy {
        ViewSharecontentBinding.inflate(layoutInflater)
    }

    private val viewModel: SaveContentViewModel by inject()
    private val navigator: AppNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.observePreviewState {
            redraw(viewState = SaveContentViewState(content = it))
        }

        binding.saveContentButton.setOnClickListener {
            viewModel.save()
            navigator.seeMainHub(context = this)
        }

        val data = SharedData(intent = intent)
        viewModel.handle(sharedData = data)
    }

    private fun redraw(viewState: SaveContentViewState) = with(viewState) {
        binding.textContent.text = resource
    }
}