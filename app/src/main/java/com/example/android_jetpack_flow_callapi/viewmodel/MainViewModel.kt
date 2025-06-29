package com.example.android_jetpack_flow_callapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_jetpack_flow_callapi.model.User
import com.example.android_jetpack_flow_callapi.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// MainViewModel.kt
class MainViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val _uiState = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<User>>> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            // Repository đã trả về đúng UiState chúng ta cần
            // ViewModel chỉ việc thu thập và cập nhật state của nó
            userRepository.getUsers()
                .collect { state ->
                    _uiState.value = state
                }
        }
    }
}


