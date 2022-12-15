package com.keepcoding.navi.dragonballapp.data.local

import androidx.room.*
import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity

@Dao
interface HeroDAO {
    @Query("SELECT * FROM hero")
    fun getAllHeroes(): List<HeroEntity>

    @Query("SELECT * FROM hero WHERE favorite = :favorite")
    fun loadAllByFavorite(favorite: Boolean): List<HeroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(hero: List<HeroEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHero(hero: HeroEntity)

    @Update
    suspend fun updateHero(hero: HeroEntity)

    @Delete
    suspend fun deleteHeroes(vararg users: HeroEntity)
}