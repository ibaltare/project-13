package com.keepcoding.navi.dragonballapp.ui.login

sealed class LoginState {
    data class Success(val token: String) : LoginState()
    data class Failure(val message : String): LoginState()
    object Loading : LoginState()
}