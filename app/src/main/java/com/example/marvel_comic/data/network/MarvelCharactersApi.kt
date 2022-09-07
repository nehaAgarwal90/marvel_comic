package com.example.marvel_comic.data.network

import com.example.marvel_comic.data.network.model.CharactersListResponse
import com.example.marvel_comic.utils.ApiResponse
import com.example.marvel_comic.utils.execute
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class MarvelCharactersApi @Inject constructor(retrofit: Retrofit) :
    IMarvelCharactersApi {
    private val apiService by lazy { retrofit.create(ApiService::class.java) }
    override suspend fun fetchMarvelCharactersList(accessToken: String, offset: Int): ApiResponse<CharactersListResponse> {
        return execute { apiService.fetchMarvelCharactersList(accessToken, "135b32572260560e531377f993297ee0", offset) }
    }

    interface ApiService {
        @GET("characters")
        suspend fun fetchMarvelCharactersList(@Query("apikey") apikey: String, @Query("hash") hash: String, @Query("offset") offset: Int): Response<CharactersListResponse>
    }
}