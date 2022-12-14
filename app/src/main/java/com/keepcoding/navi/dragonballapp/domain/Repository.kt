package com.keepcoding.navi.dragonballapp.domain

interface Repository {
    suspend fun getHeros(): List<Hero>
    suspend fun getHerosWithCache(): List<Hero>
    //suspend fun getHerosWithException(): SuperHeroListState
    //suspend fun getHeroDetail(name: String): DetailState
}