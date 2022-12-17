package com.keepcoding.navi.dragonballapp.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.keepcoding.navi.dragonballapp.databinding.FragmentLoginBinding
import androidx.navigation.fragment.findNavController
import com.keepcoding.navi.dragonballapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateAuthToken()
        setObservers()
        setListeners()
    }

    private fun validateAuthToken() {
        if (viewModel.authTokenExist())
            findNavController().navigate(R.id.action_LoginFragment_to_HomeFragment)
    }

    private fun setListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.doLogin(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner){ loginState ->
            when(loginState){
                is LoginState.Loading -> {
                    showLoading(true)
                    buttonLoginState(false)
                }
                is LoginState.Failure -> {
                    showLoading(false)
                    buttonLoginState(true)
                    showMessage(loginState.message)
                }
                is LoginState.Success -> {
                    viewModel.saveAuthentication(loginState.token)
                    findNavController().navigate(R.id.action_LoginFragment_to_HomeFragment)
                }
            }

        }
    }

    private fun showMessage(message: String){
        Toast.makeText(binding.root.context, message , Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean){
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun buttonLoginState(enabled: Boolean){
        binding.btnLogin.isEnabled = enabled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}