package com.keepcoding.navi.dragonballapp.data.mappers

import com.keepcoding.navi.dragonballapp.data.remote.response.LocalizationDTO
import com.keepcoding.navi.dragonballapp.domain.Localization
import javax.inject.Inject

class LocalizationMapper @Inject constructor(){

    fun dtoMap(localizationDto: List<LocalizationDTO>): List<Localization> {
        return localizationDto.map { Localization(it.dateShow, it.longitud, it.latitud) }
    }

}