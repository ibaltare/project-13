package com.keepcoding.navi.dragonballapp.utils

import com.keepcoding.navi.dragonballapp.data.local.model.HeroEntity
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroDTO
import com.keepcoding.navi.dragonballapp.data.remote.response.HeroID
import com.keepcoding.navi.dragonballapp.data.remote.response.LocalizationDTO
import com.keepcoding.navi.dragonballapp.domain.HeroDetail

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

    fun getHeroDetail(): HeroDetail {
        return HeroDetail(
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
                it.toDouble(),
                HeroID("ID: $it"),
                it.toDouble(),
                "ID: $it"
            )
        }
    }

    fun getFakeToken() = "Token"
}