package com.app.feature.topics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.topics.databinding.ViewTopicsBinding

class TopicsActivity: AppCompatActivity() {

    private val binding by lazy {
        ViewTopicsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}