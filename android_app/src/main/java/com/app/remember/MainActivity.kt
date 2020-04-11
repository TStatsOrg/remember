package com.app.remember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.shared.MLPTest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MLPTest().doThis()
    }
}