package com.example.color_palette.data.repository

import com.example.color_palette.data.model.ColorInfoModel
import com.example.color_palette.data.network.IColorPaletteApi
import com.example.color_palette.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalStdlibApi
@Singleton
class ColorPaletteRepository @Inject constructor(
    private val api: IColorPaletteApi,
) : IColorPaletteRepository {
    override suspend fun fetchColorInfo(hex: String): Flow<ApiResponse<ColorInfoModel>> =
        flow { emit(api.fetchColorInfo(hex)) }
}