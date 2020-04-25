package com.app.feature.topics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.topics.databinding.ViewTopicsBinding
import com.app.shared.navigation.AppNavigation
import org.koin.android.ext.android.inject

class TopicsActivity: AppCompatActivity() {

    private val navigatior: AppNavigation by inject()
    private val binding by lazy {
        ViewTopicsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addTopicButton.setOnClickListener {
            navigatior.seeAddTopic(context = this)
        }
    }
}