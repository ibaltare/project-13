package com.keepcoding.navi.dragonballapp.data.remote

import com.keepcoding.navi.dragonballapp.data.remote.request.HeroRequest
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroDTO
import com.keepcoding.navi.dragonballapp.domain.Hero
import okhttp3.Credentials
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(private val api: DragonBallAPI): RemoteDataSource {

    override suspend fun doLogin(user: String, password: String): Result<String> {
        return runCatching {  api.doLogin(Credentials.basic(user, password)) }
    }

    override suspend fun getHeroes(): Result<List<HeroDTO>> {
        return runCatching { api.getHeroes(HeroRequest()) }
    }

}