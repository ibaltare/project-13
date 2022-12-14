package com.keepcoding.navi.dragonballapp.ui.login

sealed class LoginState {
    data class LoginSuccess(val token: String) : LoginState()
    data class Error(val message : String): LoginState()
    object Loading : LoginState()
}