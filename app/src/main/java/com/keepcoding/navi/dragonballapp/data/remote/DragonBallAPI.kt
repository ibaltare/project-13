package com.keepcoding.navi.dragonballapp.data.remote

import retrofit2.http.Header
import retrofit2.http.POST

interface DragonBallAPI {

    @POST("api/auth/login")
    suspend fun doLogin(@Header("Authorization") header: String): String
}