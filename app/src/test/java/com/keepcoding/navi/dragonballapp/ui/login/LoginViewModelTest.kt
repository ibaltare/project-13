package com.keepcoding.navi.dragonballapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.keepcoding.navi.dragonballapp.domain.repository.LoginRepository
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
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    //SUT
    private lateinit var loginViewModel: LoginViewModel
    // Dependencies
    private lateinit var repository: LoginRepository

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

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
    fun `When doLogin expects succes return succes state`() = runTest{
        //given
        repository = mockk()
        loginViewModel = LoginViewModel(repository)
        coEvery { repository.doLogin("Test","Test") } returns LoginState.Success("Token")

        //when
        loginViewModel.doLogin("Test","Test")
        val actualLiveData = loginViewModel.state.getOrAwaitValue()
        //then
        Truth.assertThat(actualLiveData).isEqualTo(LoginState.Success("Token"))
    }

    @Test
    fun `When doLogin expects failure state`() = runTest{
        //given
        repository = mockk()
        loginViewModel = LoginViewModel(repository)

        //when
        loginViewModel.doLogin("","")
        val actualLiveData = loginViewModel.state.getOrAwaitValue()

        //then
        Truth.assertThat(actualLiveData).isInstanceOf(LoginState.Failure::class.java)
    }

    @Test
    fun `When authTokenExist expects true`() = runTest{
        //given
        repository = mockk()
        loginViewModel = LoginViewModel(repository)
        coEvery { repository.authTokenExist() } returns true
        //when
        val result = loginViewModel.authTokenExist()

        //then
        Truth.assertThat(result).isTrue()
    }

}