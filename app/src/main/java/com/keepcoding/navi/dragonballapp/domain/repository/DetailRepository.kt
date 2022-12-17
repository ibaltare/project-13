package com.keepcoding.navi.dragonballapp.domain.repository

import com.keepcoding.navi.dragonballapp.domain.HeroDetail

interface DetailRepository {
    suspend fun getLocalHero(heroId: String): HeroDetail
}