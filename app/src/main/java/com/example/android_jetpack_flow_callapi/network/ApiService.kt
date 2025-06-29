package com.example.android_jetpack_flow_callapi.network

import com.example.android_jetpack_flow_callapi.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
