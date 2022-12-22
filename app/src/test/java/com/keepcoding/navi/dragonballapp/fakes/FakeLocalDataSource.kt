package com.keepcoding.navi.dragonballapp.fakes

import com.keepcoding.navi.dragonballapp.data.local.LocalDataSource
import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity
import com.keepcoding.navi.dragonballapp.utils.Generator

class FakeLocalDataSource(private val success: Boolean = true): LocalDataSource {

    override fun getHeroes(): List<HeroEntity> {
        if (success) return Generator.getHeroEntityList()
        return emptyList()
    }

    override fun insertHeroes(heroDto: List<HeroEntity>) {  }

    override fun updateHero(hero: HeroEntity) {  }

    override fun deleteHeroes() {  }

    override fun getHeroesByFavorite(favorite: Boolean): List<HeroEntity> {
        if (success) return Generator.getHeroEntityList()
        return emptyList()
    }

    override fun getHeroById(id: String): HeroEntity {
        return Generator.getHeroEntity()
    }
}