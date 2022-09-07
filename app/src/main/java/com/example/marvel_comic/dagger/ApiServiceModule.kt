package com.example.marvel_comic.dagger

import com.example.marvel_comic.data.network.IMarvelCharactersApi
import com.example.marvel_comic.data.network.MarvelCharactersApi
import com.example.marvel_comic.data.repository.IMarvelCharactersRepository
import com.example.marvel_comic.data.repository.MarvelCharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApiServiceModule {

    @Binds
    @Singleton
    fun provideMarvelCharactersApi(marvelCharactersApi: MarvelCharactersApi): IMarvelCharactersApi

    @OptIn(ExperimentalStdlibApi::class)
    @Binds
    @Singleton
    fun provideMarvelCharactersRepository(marvelCharactersRepository: MarvelCharactersRepository): IMarvelCharactersRepository
}