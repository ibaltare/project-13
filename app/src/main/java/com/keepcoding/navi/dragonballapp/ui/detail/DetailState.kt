package com.keepcoding.navi.dragonballapp.ui.detail

import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import com.keepcoding.navi.dragonballapp.domain.Localization

sealed class DetailState {
    data class SuccessLocalHero(val hero: HeroDetail) : DetailState()
    data class SuccessLocalizationHero(val localizations: List<Localization>?) : DetailState()
    data class Failure(val message : String): DetailState()
    data class Loading(val show: Boolean) : DetailState()
}

sealed class LikeResultState {
    object SuccessLikeHero: LikeResultState()
    data class Failure(val message : String): LikeResultState()
}