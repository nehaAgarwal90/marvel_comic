package com.example.marvel_comic.dagger

import android.content.Context
import androidx.room.Room
import com.example.marvel_comic.data.dao.IMarvelCharacterDao
import com.example.marvel_comic.data.dao.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "Marvelcharacter")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideMarvelCharacterDao(appDatabase: AppDatabase): IMarvelCharacterDao {
        return appDatabase.marvelCharacterDao()
    }

}