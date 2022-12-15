package com.keepcoding.navi.dragonballapp.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.keepcoding.navi.dragonballapp.data.local.DragonBallDatabase
import com.keepcoding.navi.dragonballapp.data.local.HeroDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DragonBallDatabase {
        return Room.databaseBuilder(
            context,
            DragonBallDatabase::class.java, "drargon-ball-database"
        ).build()
    }

    @Provides
    fun provideDao(database: DragonBallDatabase): HeroDAO {
        return database.getDao()
    }
}