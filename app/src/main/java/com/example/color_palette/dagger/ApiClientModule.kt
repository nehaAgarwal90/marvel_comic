package com.example.color_palette.dagger

import com.example.color_palette.constants.Constants.BASE_URL_COLOR_PALETTE_APP
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object ApiClientModule {

    @Provides
    @Singleton
    fun provideColorPaletteAppUrl(): String {
        return BASE_URL_COLOR_PALETTE_APP
    }

    @Provides
    @Singleton
    fun provideColorPaletteAppRetrofit(
        baseUrl: String
    ): Retrofit {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder
                .header("Referer", "https://developer.ColorPalette.com/")
            it.proceed(requestBuilder.build())
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpLoggingInterceptor)
        builder.callTimeout(30000, TimeUnit.MILLISECONDS)
        builder.readTimeout(30000, TimeUnit.MILLISECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }
}