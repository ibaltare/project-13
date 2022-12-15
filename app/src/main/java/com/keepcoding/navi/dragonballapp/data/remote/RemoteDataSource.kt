package com.keepcoding.navi.dragonballapp.data.remote

interface RemoteDataSource {
    suspend fun doLogin(user:String, password: String):Result<String>
}