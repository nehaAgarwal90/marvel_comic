package com.example.marvel_comic.data.dao

import androidx.room.*
import com.example.marvel_comic.data.dao.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface IMarvelCharacterDao {

    @Query("SELECT * FROM MarvelCharacter")
    fun getAll(): Flow<List<MarvelCharacter>>

//    @Query("SELECT * FROM MarvelCharacter WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>

//    @Query("SELECT * FROM MarvelCharacter WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(character: List<MarvelCharacter>)

    @Delete
    suspend fun delete(character: MarvelCharacter)
}
