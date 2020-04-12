package com.app.feature.savecontent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.savecontent.databinding.ViewSharecontentBinding
import com.app.shared.business.AppState
import com.app.shared.business.AppStateReducer
import com.app.shared.features.savecontent.SharedData
import com.app.shared.features.savecontent.SharedSaveContentViewModel
import com.app.shared.redux.Store

class SaveContentActivity: AppCompatActivity() {

    private val binding: ViewSharecontentBinding by lazy {
        ViewSharecontentBinding.inflate(layoutInflater)
    }

    private val store by lazy {
        Store(initialState = AppState(), reducer = AppStateReducer)
    }

    private val viewModel: SharedSaveContentViewModel by lazy {
        SharedSaveContentViewModel(store = store)
    }

    private val redraw by lazy {
        Redraw
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.observePreviewState {
            redraw(binding, SaveContentViewState(content = it))
        }

        binding.saveContentButton.setOnClickListener {
            viewModel.save()
        }

        val data = SharedData(intent = intent)
        viewModel.handle(sharedData = data)
    }
}