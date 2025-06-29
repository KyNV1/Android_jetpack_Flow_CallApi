package com.example.android_jetpack_flow_callapi.repository

import com.example.android_jetpack_flow_callapi.model.User
import com.example.android_jetpack_flow_callapi.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository {
    // hàm này sẽ tạo ra flow
    fun getUsers(): Flow<List<User>> = flow {
        // gọi hàm suspend lấy dữ liệu từ ApiService
        val users = RetrofitInstance.api.getUsers()
        // phát (emit) kết quả được vào trong flow
        emit(users)
    }.flowOn(Dispatchers.IO)// chạy trên luồng IO
}
