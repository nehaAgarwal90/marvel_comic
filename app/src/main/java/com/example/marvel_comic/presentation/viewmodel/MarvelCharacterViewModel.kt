package com.example.marvel_comic.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_comic.constant.Constants.API_KEY
import com.example.marvel_comic.data.dao.model.MarvelCharacter
import com.example.marvel_comic.data.network.model.CharactersListResponse
import com.example.marvel_comic.data.repository.IMarvelCharactersRepository
import com.example.marvel_comic.utils.ApiResponse
import com.example.marvel_comic.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MarvelCharacterViewModel @Inject constructor(
    private val repository: IMarvelCharactersRepository
) : ViewModel() {

    val state = MutableStateFlow<List<MarvelCharacter>>(emptyList())
    var isFirstLaunch = true

    suspend fun fetchMarvelCharactersList(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchMarvelCharactersList(API_KEY, offset, isFirstLaunch)
                .collect { response: List<MarvelCharacter> ->
                    isFirstLaunch = false
                    state.value = response
                }
        }
    }

}