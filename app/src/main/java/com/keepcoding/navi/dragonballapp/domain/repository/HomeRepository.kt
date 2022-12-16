package com.keepcoding.navi.dragonballapp.domain.repository

import com.keepcoding.navi.dragonballapp.domain.Hero
import com.keepcoding.navi.dragonballapp.ui.home.HomeState

interface HomeRepository {
    suspend fun getRemoteHeroes(): HomeState
    suspend fun getHeroes(): HomeState
}