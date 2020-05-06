package com.app.feature.display

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.dependencies.navigation.Navigation
import com.app.feature.display.databinding.ViewDisplayLinkBinding
import com.app.shared.feature.display.DisplayViewModel
import org.koin.android.ext.android.inject

class DisplayLinkActivity: AppCompatActivity() {

    private val viewModel: DisplayViewModel by inject()
    private val binding by lazy { ViewDisplayLinkBinding.inflate(layoutInflater) }

    private val bookmarkId by lazy {
        intent.getIntExtra(Navigation.BOOKMARK_ID, -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.observeDisplayLinkState {
            binding.linkDisplay.loadUrl(it.url)
        }

        viewModel.displayLink(bookmarkId = bookmarkId)
    }
}