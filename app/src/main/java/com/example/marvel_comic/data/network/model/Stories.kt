package com.example.marvel_comic.data.network.model

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)