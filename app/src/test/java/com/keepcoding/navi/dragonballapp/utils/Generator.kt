package com.keepcoding.navi.dragonballapp.utils

import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroDTO
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroID
import com.keepcoding.navi.dragonballapp.data.remote.response.LocalizationDTO

object Generator {

    fun getHeroEntityList(): List<HeroEntity> {
        return (0 until 10).map {
            HeroEntity(
                "ID: $it",
                "Name $it",
                "Photo $it",
                "Description $it",
                false
            )
        }
    }

    fun getHeroEntity(): HeroEntity {
        return HeroEntity(
            "ID",
            "Name",
            "Photo",
            "Description",
            false
        )
    }

    fun getHeroDTOList(): List<HeroDTO> {
        return (0 until 10).map {
            HeroDTO(
                "ID: $it",
                "Name $it",
                "Photo $it",
                "Description $it",
                false
            )
        }
    }

    fun geLocalizationDTOList(): List<LocalizationDTO> {
        return (0 until 10).map {
            LocalizationDTO(
                "DateShow: $it",
                it as Double,
                HeroID("ID: $it"),
                it as Double,
                "ID: $it"
            )
        }
    }

    fun getFakeToken() = "Token"
}