package com.keepcoding.navi.dragonballapp.ui.home

import com.keepcoding.navi.dragonballapp.domain.Hero

sealed class HomeState {
    data class Success(val heroes: List<Hero>) : HomeState()
    data class Failure(val message : String): HomeState()
    object Loading : HomeState()
}