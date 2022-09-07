package com.example.marvel_comic.dagger

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.marvel_comic.constant.Constants.BASE_URL_MARVEL_APP
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object ApiClientModule {

    @Provides
    @Singleton
    fun provideMarvelAppUrl(): String {
        return BASE_URL_MARVEL_APP
    }

    @Provides
    @Singleton
    fun provideMarvelAppRetrofit(
        baseUrl: String
    ): Retrofit {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder
                .header("Referer", "https://developer.marvel.com/")
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

//    @Qualifier
//    @MustBeDocumented
//    @Retention(AnnotationRetention.RUNTIME)
//    annotation class MarvelAppApi()
}