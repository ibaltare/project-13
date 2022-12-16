package com.keepcoding.navi.dragonballapp.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesImp @Inject constructor(private val sharedPreference: SharedPreferences): AuthToken {

    companion object {
        private var SHARED_PREFERENCES_TOKEN = "dragon.ball.token"
    }

    override fun saveToken(token: String) {
        with(sharedPreference.edit()) {
            putString(SHARED_PREFERENCES_TOKEN, token)
            apply()
        }
    }

    override fun getToken(): String? {
        return sharedPreference.getString(SHARED_PREFERENCES_TOKEN, null)
    }

}