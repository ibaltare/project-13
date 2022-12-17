package com.keepcoding.navi.dragonballapp.data.local

import androidx.room.*
import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity

@Dao
interface HeroDAO {
    @Query("SELECT * FROM hero")
    fun getAllHeroes(): List<HeroEntity>

    @Query("SELECT * FROM hero WHERE favorite = :favorite")
    fun loadAllByFavorite(favorite: Boolean): List<HeroEntity>

    @Query("SELECT * FROM hero WHERE id = :id")
    fun getHeroById(id: String): HeroEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(heroes: List<HeroEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHero(hero: HeroEntity)

    @Update
    fun updateHero(hero: HeroEntity)

    @Query("DELETE FROM hero")
    fun deleteHeroes()
}