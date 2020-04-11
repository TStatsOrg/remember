package com.app.feature.savecontent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.savecontent.databinding.ViewSharecontentBinding
import com.app.shared.features.savecontent.SharedSaveContentViewModel

class SaveContentActivity: AppCompatActivity() {

    private val binding: ViewSharecontentBinding by lazy {
        ViewSharecontentBinding.inflate(layoutInflater)
    }

    private val viewModel: SharedSaveContentViewModel by lazy {
        SharedSaveContentViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.handleContent(fromIntent = intent)

        if (intent.action == Intent.ACTION_SEND) {

            val containsText = intent.type?.startsWith("text/") ?: false

            if (containsText) {
                val text: String? = intent.getStringExtra(Intent.EXTRA_TEXT)
                binding.textContent.text = text
            }
        }
    }
}