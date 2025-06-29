package com.example.android_jetpack_flow_callapi.network

import com.example.android_jetpack_flow_callapi.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    // bây giờ chúng ta muon nhận về toàn bộ Response
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}
