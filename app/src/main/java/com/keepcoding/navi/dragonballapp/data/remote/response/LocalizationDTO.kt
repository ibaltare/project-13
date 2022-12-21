package com.keepcoding.navi.dragonballapp.data.remote.response

import com.squareup.moshi.Json

data class LocalizationDTO(
    @Json(name = "dateShow") val dateShow: String,
    @Json(name = "longitud") val longitud: Double,
    @Json(name = "hero") val hero: HeroID,
    @Json(name = "latitud") val latitud: Double,
    @Json(name = "id") val id: String
)

data class HeroID (
    @Json(name = "id")val id: String
)
