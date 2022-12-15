package com.keepcoding.navi.dragonballapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity

@Database(entities = [HeroEntity::class], version = 1)
abstract class DragonBallDatabase : RoomDatabase() {
    abstract fun getDao(): HeroDAO
}