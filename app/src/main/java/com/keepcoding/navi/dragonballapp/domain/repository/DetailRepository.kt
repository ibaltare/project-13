package com.keepcoding.navi.dragonballapp.domain.repository

import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity
import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import com.keepcoding.navi.dragonballapp.domain.Localization
import com.keepcoding.navi.dragonballapp.ui.detail.DetailState
import com.keepcoding.navi.dragonballapp.ui.detail.LikeResultState

interface DetailRepository {
    suspend fun getLocalHero(heroId: String): HeroDetail
    suspend fun setHeroLike(hero: String): LikeResultState
    suspend fun getHeroLocalizations(heroId: String): DetailState
    suspend fun updateLocalHero(hero: HeroDetail)
}