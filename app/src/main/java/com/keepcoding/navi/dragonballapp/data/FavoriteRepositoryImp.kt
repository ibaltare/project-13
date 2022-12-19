package com.keepcoding.navi.dragonballapp.data

import com.keepcoding.navi.dragonballapp.data.local.LocalDataSource
import com.keepcoding.navi.dragonballapp.data.mappers.PresentationMapper
import com.keepcoding.navi.dragonballapp.domain.repository.FavoriteRepository
import com.keepcoding.navi.dragonballapp.ui.favorite.FavoriteState
import javax.inject.Inject

class FavoriteRepositoryImp @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val presentationMapper: PresentationMapper
): FavoriteRepository {

    override suspend fun getLocalFavoriteHeroes(): FavoriteState {
        val heroes =localDataSource.getHeroesByFavorite(true)
        if (heroes.isNotEmpty()){
            return FavoriteState.Success(presentationMapper.entityMap(heroes))
        }
        return  FavoriteState.Failure("Lista Vacía")
    }
}