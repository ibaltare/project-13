package com.keepcoding.navi.dragonballapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.keepcoding.navi.dragonballapp.R
import com.keepcoding.navi.dragonballapp.databinding.FragmentDetailBinding
import com.keepcoding.navi.dragonballapp.domain.HeroDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
        viewModel.getHeroDetail(args.heroId)
    }

    private fun setListeners() {
        binding.fabLike.setOnClickListener {
            viewModel.setHeroLike()
        }
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner){ detailState ->
            when(detailState){
                is DetailState.Failure -> {
                    showLoading(false)
                    showMessage(detailState.message)
                }
                is DetailState.Loading -> {
                    showLoading(true)
                }
                is DetailState.SuccessLocalhero -> {
                    setHeroDetail(detailState.hero)
                }
                is DetailState.SuccessHeroLocalization -> {
                    showLoading(false)
                }
            }
        }

        viewModel.like.observe(viewLifecycleOwner){ like ->
            when(like){
                true -> binding.fabLike.backgroundTintList = AppCompatResources.getColorStateList(binding.root.context, R.color.orange)
                false -> binding.fabLike.backgroundTintList = AppCompatResources.getColorStateList(binding.root.context, R.color.teal_200)
            }
        }
    }

    private fun setHeroDetail(hero: HeroDetail){
        with(binding){
            heroImage.load(hero.photo)
            heroName.text = hero.name
            heroDetail.setText(hero.description)
        }
    }

    private fun showMessage(message: String){
        Toast.makeText(binding.root.context, message , Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean){
        binding.pbDetail.visibility = if (show) View.VISIBLE else View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}