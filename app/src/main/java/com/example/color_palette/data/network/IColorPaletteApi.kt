package com.example.color_palette.data.network

import com.example.color_palette.data.model.ColorInfoModel
import com.example.color_palette.utils.ApiResponse

interface IColorPaletteApi {
    suspend fun fetchColorInfo(hex: String): ApiResponse<ColorInfoModel>
}