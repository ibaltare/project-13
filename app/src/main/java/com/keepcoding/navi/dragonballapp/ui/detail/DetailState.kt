package com.keepcoding.navi.dragonballapp.ui.detail

import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import com.keepcoding.navi.dragonballapp.domain.Localization

sealed class DetailState {
    data class SuccessLocalhero(val hero: HeroDetail) : DetailState()
    data class SuccessHeroLocalization(val localizations: List<Localization>?) : DetailState()
    data class Failure(val message : String): DetailState()
    object Loading : DetailState()
}