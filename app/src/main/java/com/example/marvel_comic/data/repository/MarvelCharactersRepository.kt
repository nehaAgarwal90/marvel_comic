package com.example.marvel_comic.data.repository

import com.example.marvel_comic.data.dao.IMarvelCharacterDao
import com.example.marvel_comic.data.dao.model.MarvelCharacter
import com.example.marvel_comic.data.network.IMarvelCharactersApi
import com.example.marvel_comic.data.network.model.CharactersListResponse
import com.example.marvel_comic.utils.ApiResponse
import com.example.marvel_comic.utils.Success
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton
import com.example.marvel_comic.data.network.model.toDaoModel

@ExperimentalStdlibApi
@Singleton
class MarvelCharactersRepository @Inject constructor(
    private val api: IMarvelCharactersApi,
    private val marvelCharacterDao: IMarvelCharacterDao
) :
    IMarvelCharactersRepository {


    override suspend fun fetchMarvelCharactersList(
        accessToken: String,
        offset: Int,
        isFirstLaunch: Boolean
    ) =
        flow {
            if (isFirstLaunch) {
                marvelCharacterDao.getAll().collect {
                    if (it.isNotEmpty()) {
                        emit(it)
                    } else {
                        val output = api.fetchMarvelCharactersList(accessToken, offset)
                            .let { response: ApiResponse<CharactersListResponse> ->
                                when (response) {
                                    is Success -> {
                                        val list =
                                            (response.data as CharactersListResponse).data.toDaoModel()
                                        marvelCharacterDao.insertAll(list)
                                        list
                                    }
                                    else -> {
                                        emptyList<MarvelCharacter>()
                                    }
                                }
                            }
                        emit(output)
                    }
                }
            } else {
                val output = api.fetchMarvelCharactersList(accessToken, offset)
                    .let { response: ApiResponse<CharactersListResponse> ->
                        when (response) {
                            is Success -> {
                                val list =
                                    (response.data as CharactersListResponse).data.toDaoModel()
                                marvelCharacterDao.insertAll(list)
                                list
                            }
                            else -> {
                                emptyList<MarvelCharacter>()
                            }
                        }
                    }
                emit(output)
            }
        }
}