package com.example.marvel_comic.utils

sealed class ApiResponse<out T>
data class Success<T>(val data: T?) : ApiResponse<T>()
data class ApiError(val error: String, val code: String?) : ApiResponse<Nothing>()
data class NetworkError(val error: String) : ApiResponse<Nothing>()
object ApiLoading : ApiResponse<Nothing>()