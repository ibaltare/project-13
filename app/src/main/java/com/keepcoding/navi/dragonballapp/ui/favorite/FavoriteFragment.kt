package com.keepcoding.navi.dragonballapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keepcoding.navi.dragonballapp.databinding.FragmentFavoriteBinding
import com.keepcoding.navi.dragonballapp.ui.home.HeroListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val viewModel: FavoriteViewModel by viewModels()
    private val binding get() = _binding!!
    private val adapter = HeroListAdapter {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        createRecycler()
        viewModel.getHeroes()
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state){
                is FavoriteState.Failure -> {
                    showLoading(false)
                    showMessage(state.message)
                }
                is FavoriteState.Loading -> showLoading(true)
                is FavoriteState.Success -> {
                    showLoading(false)
                    adapter.submitList(state.heroes)
                }
            }
        }
    }

    private fun createRecycler(){
        binding.heroList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
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