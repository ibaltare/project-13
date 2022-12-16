package com.keepcoding.navi.dragonballapp.data.remote

import com.keepcoding.navi.dragonballapp.data.remote.request.HeroRequest
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroDTO
import com.keepcoding.navi.dragonballapp.domain.Hero
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DragonBallAPI {

    @POST("api/auth/login")
    suspend fun doLogin(@Header("Authorization") header: String): String

    @POST("api/heros/all")
    suspend fun getHeroes(@Body heroRequest: HeroRequest): List<HeroDTO>
}