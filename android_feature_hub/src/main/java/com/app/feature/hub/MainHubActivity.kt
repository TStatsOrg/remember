package com.app.feature.hub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.feature.hub.databinding.ViewMainhubBinding
import com.app.shared.feature.mainhub.MainHubViewModel
import org.koin.android.ext.android.inject

class MainHubActivity: AppCompatActivity() {

    private val viewModel: MainHubViewModel by inject()

    private val binding by lazy {
        ViewMainhubBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}