package com.example.marvel_comic.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvel_comic.databinding.ActivityCharacterDetailsBinding
import com.example.marvel_comic.databinding.ActivityCharactersListBinding
import com.example.marvel_comic.presentation.adapter.MarvelCharacterGridViewAdapter
import com.example.marvel_comic.presentation.viewmodel.MarvelCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterDetails : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding
    private var offset = 0

    val viewModel by viewModels<MarvelCharacterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}