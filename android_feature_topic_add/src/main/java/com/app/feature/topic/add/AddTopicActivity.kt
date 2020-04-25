package com.app.feature.topic.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.topic.add.databinding.ViewAddTopicBinding
import com.app.shared.feature.addtopic.AddTopicViewModel
import org.koin.android.ext.android.inject

class AddTopicActivity: AppCompatActivity() {

    private val viewModel: AddTopicViewModel by inject()
    private val binding by lazy {
        ViewAddTopicBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.observeTopicsSaved {
            finish()
        }

        binding.saveTopicButton.setOnClickListener {
            val topicName = binding.topicNameInputText.text?.toString() ?: ""
            viewModel.addTopic(name = topicName)
        }
    }
}