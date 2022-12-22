package com.keepcoding.navi.dragonballapp.data

import com.keepcoding.navi.dragonballapp.data.local.AuthToken
import com.keepcoding.navi.dragonballapp.data.remote.RemoteDataSource
import com.keepcoding.navi.dragonballapp.domain.repository.LoginRepository
import com.keepcoding.navi.dragonballapp.ui.login.LoginState
import retrofit2.HttpException
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val authToken: AuthToken
): LoginRepository {

    override suspend fun doLogin(user: String, password: String): LoginState {
        val result = remoteDataSource.doLogin(user,password)
        return when {
            result.isSuccess -> LoginState.Success(result.getOrThrow())
            else -> {
                when (val exception = result.exceptionOrNull()){
                    is HttpException -> {
                        when(exception.code()){
                            401 -> LoginState.Failure("Usuario no autenticado")
                            else -> LoginState.Failure("Error de Red")
                        }
                    }
                    else -> {
                        LoginState.Failure(result.exceptionOrNull()?.message ?: "Error!!")
                    }
                }
            }
        }
    }

    override fun saveToken(token: String) {
        authToken.saveToken(token)
    }

    override fun authTokenExist(): Boolean {
        if (authToken.getToken() != null) return true
        return false
    }

}