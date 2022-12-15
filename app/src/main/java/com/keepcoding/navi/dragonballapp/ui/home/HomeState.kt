package com.keepcoding.navi.dragonballapp.ui.home

sealed class HomeState {
    data class Success(val token: String) : HomeState()
    data class Failure(val message : String): HomeState()
    object Loading : HomeState()
}