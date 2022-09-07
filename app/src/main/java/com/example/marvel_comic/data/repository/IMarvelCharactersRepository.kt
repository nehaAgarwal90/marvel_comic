package com.example.marvel_comic.data.repository

import com.example.marvel_comic.data.dao.model.MarvelCharacter
import com.example.marvel_comic.data.network.model.CharactersListResponse
import com.example.marvel_comic.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IMarvelCharactersRepository {
    suspend fun fetchMarvelCharactersList(accessToken: String, offset: Int, isFirstLaunch: Boolean): Flow<List<MarvelCharacter>>
}