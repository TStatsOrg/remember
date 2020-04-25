package com.app.feature.topic.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.topic.add.databinding.ViewAddTopicBinding

class AddTopicActivity: AppCompatActivity() {

    private val binding by lazy {
        ViewAddTopicBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.saveTopicButton.setOnClickListener {

        }
    }
}