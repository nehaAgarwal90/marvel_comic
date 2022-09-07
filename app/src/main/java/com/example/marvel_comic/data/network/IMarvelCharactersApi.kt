package com.example.marvel_comic.data.network

import com.example.marvel_comic.data.network.model.CharactersListResponse
import com.example.marvel_comic.utils.ApiResponse

interface IMarvelCharactersApi {
    suspend fun fetchMarvelCharactersList(accessToken: String, offset: Int): ApiResponse<CharactersListResponse>
}