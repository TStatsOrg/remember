package com.app.feature.preview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.feature.preview.databinding.ViewPreviewBinding
import com.app.shared.feature.preview.PreviewViewModel
import com.app.views.navigation.Navigation
import org.koin.android.ext.android.inject

class PreviewActivity: AppCompatActivity() {

    private val adapter: PreviewsAdapter by inject()
    private val layoutManager = LinearLayoutManager(this)
    private val animator = DefaultItemAnimator()
    private val viewModel: PreviewViewModel<Intent> by inject()
    private val navigator: Navigation by inject()
    private val binding: ViewPreviewBinding by lazy { ViewPreviewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.previewsRecyclerView.adapter = adapter
        binding.previewsRecyclerView.layoutManager = layoutManager
        binding.previewsRecyclerView.itemAnimator = animator

        viewModel.observePreviewState {
            val viewState = PreviewsViewState(state = it)
            redraw(viewState = viewState)
            binding.previewLoadingSpinner.visibility = viewState.spinnerVisibility
        }

        viewModel.observeBookmarkSaved {
            navigator.seeEditBookmark(context = this, forEditingBookmark = it)
            finish()
        }

        binding.saveContentButton.setOnClickListener {
            viewModel.save()
        }

        viewModel.capture(input = intent)
    }

    private fun redraw(viewState: PreviewsViewState) = with(viewState) {
        adapter.redraw(viewState = viewState)
        binding.saveContentButton.isEnabled = true
    }

    override fun onDestroy() {
        viewModel.cleanup()
        super.onDestroy()
    }
}