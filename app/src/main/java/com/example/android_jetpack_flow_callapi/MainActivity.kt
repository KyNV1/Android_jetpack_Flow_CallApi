package com.example.android_jetpack_flow_callapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_jetpack_flow_callapi.viewmodel.MainViewModel
import com.example.android_jetpack_flow_callapi.viewmodel.UiState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var textValue: TextView
    private lateinit var process: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textValue = findViewById<TextView>(R.id.ValueText)
        process = findViewById<ProgressBar>(R.id.process_bar)
        observerUiState()
    }

    private fun observerUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {state->
                    when (state) {
                        is UiState.Error -> {
                            process.visibility = View.GONE
                            textValue.visibility = View.GONE
                        }

                        UiState.Loading -> {
                            process.visibility = View.VISIBLE
                            textValue.visibility = View.GONE
                        }

                        is UiState.Success -> {
                            process.visibility = View.GONE
                            textValue.visibility = View.VISIBLE
                            textValue.text = state.data.toString()
                        }
                    }
                }
            }
        }
    }
}