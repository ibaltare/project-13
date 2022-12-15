package com.keepcoding.navi.dragonballapp.dependencyinjection

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.keepcoding.navi.dragonballapp.data.local.DragonBallDatabase
import com.keepcoding.navi.dragonballapp.data.local.SharedPreferencesImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    private var SHARED_PREFERENCES_FILE_KEY = "navi.dragonballapp.preferences"

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
    }

}