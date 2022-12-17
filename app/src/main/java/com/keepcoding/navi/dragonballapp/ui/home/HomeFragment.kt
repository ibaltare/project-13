package com.keepcoding.navi.dragonballapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keepcoding.navi.dragonballapp.R
import com.keepcoding.navi.dragonballapp.databinding.FragmentHomeBinding
import com.keepcoding.navi.dragonballapp.ui.login.LoginState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val binding get() = _binding!!
    private val adapter = HeroListAdapter {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
        createRecycler()
        viewModel.getHeroes()
    }


    private fun setListeners() {
        binding.fabExit.setOnClickListener { viewModel.Logout() }
        binding.fabFavorite.setOnClickListener {  }
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner){ homeState ->
            when(homeState){
                is HomeState.Loading -> {
                    showLoading(true)
                }
                is HomeState.Failure -> {
                    showLoading(false)
                    showMessage(homeState.message)
                }
                is HomeState.Success -> {
                    showLoading(false)
                    adapter.submitList(homeState.heroes)
                }
                is HomeState.Logout -> {
                    activity?.finish()
                }
            }
        }
    }

    private fun createRecycler(){
        binding.heroList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        binding.heroList.adapter = adapter
    }

    private fun showMessage(message: String){
        Toast.makeText(binding.root.context, message , Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean){
        binding.pbHome.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}