package com.keepcoding.navi.dragonballapp.domain.repository

import com.keepcoding.navi.dragonballapp.ui.favorite.FavoriteState

interface FavoriteRepository {
    suspend fun getLocalFavoriteHeroes(): FavoriteState
}