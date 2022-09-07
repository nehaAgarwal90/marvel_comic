package com.example.marvel_comic.data.network.model

import com.example.marvel_comic.data.dao.model.MarvelCharacter

data class CharactersListResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: CharacterData,
    val etag: String,
    val status: String
)

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)

data class CharacterData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)

fun CharacterData.toDaoModel(): List<MarvelCharacter> {
    return this.results.mapNotNull { character ->
        return@mapNotNull if (character != null) {
            MarvelCharacter(
                character.id.toString(),
                character.name,
                character.description,
                "${character.thumbnail.path}.${character.thumbnail.extension}")
        } else {
            null
        }
    }
}