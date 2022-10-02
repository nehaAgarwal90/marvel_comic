package com.example.color_palette.data.network

import com.example.color_palette.data.model.ColorInfoModel
import com.example.color_palette.utils.ApiResponse
import com.example.color_palette.utils.execute
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class ColorPaletteApi @Inject constructor(retrofit: Retrofit) :
    IColorPaletteApi {
    private val apiService by lazy { retrofit.create(ApiService::class.java) }


    override suspend fun fetchColorInfo(hex: String): ApiResponse<ColorInfoModel> {
        return execute { apiService.fetchColorInfo(hex) }    }

    interface ApiService {
        @GET("id")
        suspend fun fetchColorInfo(@Query("hex") hex: String): Response<ColorInfoModel>
    }
}