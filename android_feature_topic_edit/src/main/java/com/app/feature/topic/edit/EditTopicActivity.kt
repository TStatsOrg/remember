package com.app.feature.topic.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.dependencies.navigation.Navigation
import com.app.feature.topic.edit.databinding.ViewEditTopicBinding
import com.app.shared.feature.edittopic.EditTopicViewModel
import org.koin.android.ext.android.inject

class EditTopicActivity: AppCompatActivity() {

    private val viewModel: EditTopicViewModel by inject()
    private val binding by lazy {
        ViewEditTopicBinding.inflate(layoutInflater)
    }

    private val topicId by lazy {
        intent.getIntExtra(Navigation.TOPIC_ID, -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.updateTopicButton.setOnClickListener {
            val currentName = binding.topicNameInputText.text?.toString() ?: ""
            viewModel.updateTopic(topicId = topicId, name = currentName)
        }

        viewModel.observeTopicUpdated {
            finish()
        }

        viewModel.observeEditTopicState {
            binding.topicNameInputText.setText(it.topic.name)
            binding.topicNameInputText.setSelection(it.topic.name.length)
        }

        viewModel.loadEditableTopic(forTopicId = topicId)
    }

    override fun onDestroy() {
        viewModel.cleanup()
        super.onDestroy()
    }
}