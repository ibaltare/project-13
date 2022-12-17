package com.keepcoding.navi.dragonballapp.data.local

interface AuthToken {
    fun saveToken(token: String)
    fun getToken(): String?
    fun deleteToken()
}