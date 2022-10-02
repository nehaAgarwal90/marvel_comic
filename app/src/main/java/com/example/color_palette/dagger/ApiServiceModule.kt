package com.example.color_palette.dagger

import com.example.color_palette.data.network.ColorPaletteApi
import com.example.color_palette.data.network.IColorPaletteApi
import com.example.color_palette.data.repository.ColorPaletteRepository
import com.example.color_palette.data.repository.IColorPaletteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class) interface ApiServiceModule {

    @Binds
    @Singleton
    fun provideColorPaletteApi(colorPaletteApi: ColorPaletteApi): IColorPaletteApi

    @OptIn(ExperimentalStdlibApi::class)
    @Binds
    @Singleton
    fun provideColorPaletteRepository(colorPaletteRepository: ColorPaletteRepository): IColorPaletteRepository
}