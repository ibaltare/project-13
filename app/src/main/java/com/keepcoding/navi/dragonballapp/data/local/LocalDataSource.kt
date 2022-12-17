package com.keepcoding.navi.dragonballapp.data.local

import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity

interface LocalDataSource {
    fun getHeroes(): List<HeroEntity>
    fun insertHeroes(heroDto: List<HeroEntity>)
    fun updateHero(hero: HeroEntity)
    fun deleteHeroes()
    fun getHeroesByFavorite(favorite: Boolean): List<HeroEntity>
    fun getHeroById(id: String): HeroEntity
}