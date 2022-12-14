package com.keepcoding.navi.dragonballapp.data.mappers

import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroDTO
import com.keepcoding.navi.dragonballapp.domain.Hero
import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import javax.inject.Inject

class EntityMapper @Inject constructor(){

    fun dtoMap(heroDto: List<HeroDTO>): List<HeroEntity> {
        return heroDto.map { HeroEntity(it.id,it.name,it.photo,it.description,it.favorite) }
    }

    fun pdMap(hero: HeroDetail): HeroEntity {
        return HeroEntity(hero.id,hero.name,hero.photo,hero.description,hero.favorite)
    }
}