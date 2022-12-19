package com.keepcoding.navi.dragonballapp.data.remote

import com.keepcoding.navi.dragonballapp.data.remote.request.HeroLikeRequest
import com.keepcoding.navi.dragonballapp.data.remote.request.HeroLocalizationRequest
import com.keepcoding.navi.dragonballapp.data.remote.request.HeroRequest
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroDTO
import com.keepcoding.navi.dragonballapp.data.remote.response.LocalizationDTO
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

    override suspend fun setHeroLike(hero: String): Result<Unit> {
        return runCatching { api.setHeroLike(HeroLikeRequest(hero)) }
    }

    override suspend fun getHeroLocalizations(id: String): Result<List<LocalizationDTO>> {
        return runCatching { api.getHeroLocalizations(HeroLocalizationRequest(id)) }
    }

}