package com.keepcoding.navi.dragonballapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.keepcoding.navi.dragonballapp.domain.repository.HomeRepository
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
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    //SUT
    private lateinit var homeViewModel: HomeViewModel
    //Dependencies
    private lateinit var repository: HomeRepository

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
    fun `When getHeroes expects success returns list of super heroes`() = runTest{
        //given
        repository = mockk()
        homeViewModel = HomeViewModel(repository)
        coEvery { repository.getHeroes() } returns HomeState.Success(Generator.getHeroList())

        //when
        homeViewModel.getHeroes()
        val actualLiveData = homeViewModel.state.getOrAwaitValue()

        //then
        Truth.assertThat((actualLiveData as HomeState.Success).heroes)
            .containsExactlyElementsIn(Generator.getHeroList())

    }

    @Test
    fun `When getHeroes expects failure state`() = runTest{
        //given
        repository = mockk()
        homeViewModel = HomeViewModel(repository)
        coEvery { repository.getHeroes() } returns HomeState.Failure("Error")

        //when
        homeViewModel.getHeroes()
        val actualLiveData = homeViewModel.state.getOrAwaitValue()

        //then
        Truth.assertThat(actualLiveData).isInstanceOf(HomeState.Failure::class.java)
    }
}