package com.keepcoding.navi.dragonballapp.domain.repository

import com.keepcoding.navi.dragonballapp.ui.login.LoginState

interface LoginRepository {
    suspend fun doLogin(user: String, password: String): LoginState
    fun saveToken(token: String)
    fun authTokenExist(): Boolean
}