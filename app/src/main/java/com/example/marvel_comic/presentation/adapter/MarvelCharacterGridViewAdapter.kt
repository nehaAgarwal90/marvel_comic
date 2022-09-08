package com.example.marvel_comic.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel_comic.R
import com.example.marvel_comic.data.dao.model.MarvelCharacter
import com.example.marvel_comic.databinding.GridItemMarvelCharacterBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class MarvelCharacterGridViewAdapter @Inject constructor(
) : ListAdapter<MarvelCharacter, MarvelCharacterGridViewAdapter.MarvelCharacterHolder>(DiffCallback) {
    var onItemClick: ((String) -> Unit)? = null

    inner class MarvelCharacterHolder(private val binding: GridItemMarvelCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MarvelCharacter, context: Context) {
            binding.apply {
                character = model
                Glide.with(context).load(model.imageURL).into(image)
                isBookmarked = true
                executePendingBindings()
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<MarvelCharacter>() {
        override fun areItemsTheSame(
            oldItem: MarvelCharacter,
            newItem: MarvelCharacter
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MarvelCharacter,
            newItem: MarvelCharacter
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MarvelCharacterHolder, position: Int) {
        getItem(position)?.let { character ->
            holder.bind(character, holder.itemView.context)

            holder.itemView.setOnClickListener {
                onItemClick?.invoke("character")
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MarvelCharacterHolder {
        val binding: GridItemMarvelCharacterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.grid_item_marvel_character,
            viewGroup,
            false
        )
        return MarvelCharacterHolder(binding)
    }
}