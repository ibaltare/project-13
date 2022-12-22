package com.keepcoding.navi.dragonballapp.fakes

import com.keepcoding.navi.dragonballapp.data.remote.RemoteDataSource
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroDTO
import com.keepcoding.navi.dragonballapp.data.remote.response.LocalizationDTO
import com.keepcoding.navi.dragonballapp.utils.Generator

class FakeRemoteDataSource(private val success: Boolean): RemoteDataSource {

    override suspend fun doLogin(user: String, password: String): Result<String> {
        if (success)
            return Result.success("Token")
        else
            return Result.failure(Throwable("Login Fail"))
    }

    override suspend fun getHeroes(): Result<List<HeroDTO>> {
        if (success)
            return Result.success(Generator.getHeroDTOList())
        else
            return Result.failure(Throwable("Get Hero Fail"))
    }

    override suspend fun setHeroLike(hero: String): Result<Unit> {
        if (success)
            return Result.success(Unit)
        else
            return Result.failure(Throwable("Like Fail"))
    }

    override suspend fun getHeroLocalizations(id: String): Result<List<LocalizationDTO>> {
        if (success)
            return Result.success(Generator.geLocalizationDTOList())
        else
            return Result.failure(Throwable("Get Locations Fail"))
    }
}