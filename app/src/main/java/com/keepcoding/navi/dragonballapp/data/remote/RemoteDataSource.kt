package com.keepcoding.navi.dragonballapp.data.remote

import com.keepcoding.navi.dragonballapp.data.remote.response.HeroDTO
import com.keepcoding.navi.dragonballapp.data.remote.response.LocalizationDTO

interface RemoteDataSource {
    suspend fun doLogin(user:String, password: String):Result<String>
    suspend fun getHeroes():Result<List<HeroDTO>>
    suspend fun setHeroLike(hero: String)
    suspend fun getHeroLocalizations(id: String): Result<List<LocalizationDTO>>
}