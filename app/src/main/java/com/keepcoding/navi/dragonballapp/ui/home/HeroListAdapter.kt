package com.keepcoding.navi.dragonballapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.keepcoding.navi.dragonballapp.databinding.HeroItemListBinding
import com.keepcoding.navi.dragonballapp.domain.Hero

class HeroListAdapter(private val clickListener: (String) -> (Unit)):
    ListAdapter<Hero,HeroListAdapter.HeroViewHolder>(HeroDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(
            HeroItemListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HeroViewHolder(private val binding: HeroItemListBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var hero: Hero

        init {
            binding.root.setOnClickListener {
                clickListener(hero.id)
            }
        }

        fun bind(hero: Hero){
            this.hero = hero
            with(binding){
                heroName.text = hero.name
                heroImage.load(hero.photo)
                heroLike.visibility = if (hero.favorite) View.VISIBLE else View.GONE
            }
        }
    }

    class HeroDiffCallback: DiffUtil.ItemCallback<Hero>() {

        override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem == newItem
        }
    }

}