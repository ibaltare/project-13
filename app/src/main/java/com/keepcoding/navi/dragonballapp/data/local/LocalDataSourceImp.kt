package com.keepcoding.navi.dragonballapp.data.local

import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(private val dao:HeroDAO): LocalDataSource {

    override fun getHeroes(): List<HeroEntity> {
        return dao.getAllHeroes()
    }

    override fun insertHeroes(heroDto: List<HeroEntity>) {
        dao.insertAll(heroDto)
    }

    override fun updateHero(hero: HeroEntity) {
        dao.updateHero(hero)
    }

    override fun deleteHeroes(localHeroes: List<HeroEntity>) {
        dao.deleteHeroes(localHeroes)
    }

    override fun getHeroesByFavorite(favorite: Boolean): List<HeroEntity> {
        return dao.loadAllByFavorite(favorite)
    }
}