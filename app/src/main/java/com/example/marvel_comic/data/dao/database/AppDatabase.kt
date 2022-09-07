package com.example.marvel_comic.data.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvel_comic.data.dao.IMarvelCharacterDao
import com.example.marvel_comic.data.dao.model.MarvelCharacter

@Database(entities = [MarvelCharacter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun marvelCharacterDao(): IMarvelCharacterDao
}