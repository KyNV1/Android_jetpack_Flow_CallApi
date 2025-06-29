package com.example.android_jetpack_flow_callapi.repository

import com.example.android_jetpack_flow_callapi.model.User
import com.example.android_jetpack_flow_callapi.network.RetrofitInstance
import com.example.android_jetpack_flow_callapi.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository {
    fun getUsers(): Flow<UiState<List<User>>> = flow {
        // Luôn phát ra Loading trước khi thực hiện công việc
        emit(UiState.Loading)

        try {
            val response = RetrofitInstance.api.getUsers()

            // 1. Kiểm tra "kiện hàng" có nguyên vẹn không
            if (response.isSuccessful) {
                // 2. Mở hộp lấy dữ liệu
                val users = response.body()
                if (users != null) {
                    // Thành công, phát ra dữ liệu
                    emit(UiState.Success(users))
                } else {
                    // Body rỗng, coi như là một dạng lỗi
                    emit(UiState.Error("Không có dữ liệu trả về"))
                }
            } else {
                // 3. Xử lý khi API trả về lỗi (mã 4xx, 5xx)
                val errorMsg = response.errorBody()?.string() ?: "Lỗi không xác định"
                emit(UiState.Error("Lỗi ${response.code()}: $errorMsg"))
            }
        } catch (e: Exception) {
            // 4. Xử lý các lỗi ngoại lệ khác (ví dụ: không có mạng)
            emit(UiState.Error(e.message ?: "Có lỗi xảy ra"))
        }

    }.flowOn(Dispatchers.IO)
}