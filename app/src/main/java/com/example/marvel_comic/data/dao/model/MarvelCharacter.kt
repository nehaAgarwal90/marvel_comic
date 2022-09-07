package com.example.marvel_comic.data.dao.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MarvelCharacter(
    @PrimaryKey
    val characterId: String,
    val name: String,
    val description: String? = null,
    val imageURL: String? = null,
    val isBookmarked: Boolean = false
)