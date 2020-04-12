package com.app.feature.hub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.hub.databinding.ViewMainhubBinding

class MainHubActivity: AppCompatActivity() {

    private val binding by lazy {
        ViewMainhubBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}