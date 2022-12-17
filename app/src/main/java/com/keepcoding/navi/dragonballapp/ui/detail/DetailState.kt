package com.keepcoding.navi.dragonballapp.ui.detail

import com.keepcoding.navi.dragonballapp.domain.HeroDetail

sealed class DetailState {
    data class SuccessLocalhero(val hero: HeroDetail) : DetailState()
    data class Failure(val message : String): DetailState()
    object Loading : DetailState()
}