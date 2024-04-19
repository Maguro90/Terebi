package com.maguro.terebi.data

sealed interface RequestResponse<out T> {

    data class Success<T>(val data: T) : RequestResponse<T>

    interface Error : RequestResponse<Nothing> {
        val cause: Throwable?
    }
}