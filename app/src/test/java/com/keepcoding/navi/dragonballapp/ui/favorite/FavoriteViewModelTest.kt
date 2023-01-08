package com.keepcoding.navi.dragonballapp.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.keepcoding.navi.dragonballapp.domain.repository.FavoriteRepository
import com.keepcoding.navi.dragonballapp.utils.Generator
import com.keepcoding.navi.dragonballapp.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    //SUT
    private lateinit var favoriteViewModel: FavoriteViewModel
    //Dependencies
    private lateinit var repository: FavoriteRepository

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `When getHeroes expects success returns list of favorite heroes`() = runTest{
        //given
        repository = mockk()
        favoriteViewModel = FavoriteViewModel(repository)
        coEvery { repository.getLocalFavoriteHeroes() } returns FavoriteState.Success(Generator.getHeroList())

        //when
        favoriteViewModel.getHeroes()
        val actualLiveData = favoriteViewModel.state.getOrAwaitValue()

        //then
        Truth.assertThat((actualLiveData as FavoriteState.Success).heroes)
            .containsExactlyElementsIn(Generator.getHeroList())
    }

    @Test
    fun `When getHeroes expects failure state`() = runTest{
        //given
        repository = mockk()
        favoriteViewModel = FavoriteViewModel(repository)
        coEvery { repository.getLocalFavoriteHeroes() } returns FavoriteState.Failure("Error")

        //when
        favoriteViewModel.getHeroes()
        val actualLiveData = favoriteViewModel.state.getOrAwaitValue()

        //then
        Truth.assertThat(actualLiveData).isInstanceOf(FavoriteState.Failure::class.java)
    }

}