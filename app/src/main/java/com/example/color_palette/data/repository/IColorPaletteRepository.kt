package com.example.color_palette.data.repository

import com.example.color_palette.data.model.ColorInfoModel
import com.example.color_palette.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IColorPaletteRepository {
    suspend fun fetchColorInfo(hex: String): Flow<ApiResponse<ColorInfoModel>>
}