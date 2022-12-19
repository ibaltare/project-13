package com.keepcoding.navi.dragonballapp.ui.favorite

import com.keepcoding.navi.dragonballapp.domain.Hero

sealed class FavoriteState {
    data class Success(val heroes: List<Hero>) : FavoriteState()
    data class Failure(val message : String): FavoriteState()
    object Loading : FavoriteState()
}