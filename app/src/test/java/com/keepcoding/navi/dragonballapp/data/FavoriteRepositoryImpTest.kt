package com.keepcoding.navi.dragonballapp.data

import com.google.common.truth.Truth
import com.keepcoding.navi.dragonballapp.data.mappers.PresentationMapper
import com.keepcoding.navi.dragonballapp.fakes.FakeLocalDataSource
import com.keepcoding.navi.dragonballapp.ui.favorite.FavoriteState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteRepositoryImpTest{
    //SUT
    private lateinit var favoriteRepositoryImp: FavoriteRepositoryImp
    private lateinit var presentationMapper: PresentationMapper

    @Before
    fun setUp(){
        presentationMapper = PresentationMapper()
    }

    @Test
    fun `When getLocalFavoriteHeroes expects success return FavoriteState_Success`() = runTest{
        //given
        val localDataSource = FakeLocalDataSource()
        favoriteRepositoryImp = FavoriteRepositoryImp(
            localDataSource,
            presentationMapper
        )

        //when
        val actual = favoriteRepositoryImp.getLocalFavoriteHeroes()

        //then
        Truth.assertThat(actual).isInstanceOf(FavoriteState::class.java)
        Truth.assertThat(actual).isInstanceOf(FavoriteState.Success::class.java)
        Truth.assertThat((actual as FavoriteState.Success).heroes.count()).isGreaterThan(0)
    }

    @Test
    fun `When getLocalFavoriteHeroes expects empty list`() = runTest{
        //given
        val localDataSource = FakeLocalDataSource(false)
        favoriteRepositoryImp = FavoriteRepositoryImp(
            localDataSource,
            presentationMapper
        )

        //when
        val actual = favoriteRepositoryImp.getLocalFavoriteHeroes()

        //then
        Truth.assertThat(actual).isInstanceOf(FavoriteState::class.java)
        Truth.assertThat(actual).isInstanceOf(FavoriteState.Failure::class.java)
        Truth.assertThat(actual).isEqualTo(FavoriteState.Failure("Lista Vacía"))
    }
}