package com.keepcoding.navi.dragonballapp.data.remote

import okhttp3.Credentials
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(private val api: DragonBallAPI): RemoteDataSource {

    override suspend fun doLogin(user: String, password: String): Result<String> {
        return runCatching {  api.doLogin(Credentials.basic(user, password)) }
    }

}