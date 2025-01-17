package com.rishabhsingh.upstoxassignment.api

sealed class NetworkState<out T> {
    data object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Failure(val errorMessage: String) : NetworkState<Nothing>()
    data object Empty : NetworkState<Nothing>()
}