package com.keepcoding.navi.dragonballapp.data.mappers

import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroDTO
import com.keepcoding.navi.dragonballapp.domain.Hero
import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import javax.inject.Inject

class PresentationMapper @Inject constructor() {

    fun dtoMap(heroDto: List<HeroDTO>): List<Hero> {
        return heroDto.map { Hero(it.id,it.name,it.photo,it.favorite) }
    }

    fun entityMap(heroEntity: List<HeroEntity>): List<Hero> {
        return heroEntity.map { Hero(it.id,it.name,it.photo,it.favorite) }
    }

    fun entityDetailMap(heroEntity: HeroEntity): HeroDetail {
        return HeroDetail(
            heroEntity.id,heroEntity.name, heroEntity.photo, heroEntity.description, heroEntity.favorite
        )
    }
}