package com.example.android_jetpack_flow_callapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_jetpack_flow_callapi.model.User
import com.example.android_jetpack_flow_callapi.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _uiState = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<User>>> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            repository.getUsers()
                .onStart { _uiState.value = UiState.Loading }
                .catch { exeception ->
                    _uiState.value = UiState.Error(exeception.message.toString())
                }
                .collect { user ->
                    _uiState.value = UiState.Success(user)
                }

        }
    }
}

