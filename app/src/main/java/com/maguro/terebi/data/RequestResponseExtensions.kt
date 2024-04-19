package com.maguro.terebi.data

inline fun <T, R> RequestResponse<T>.map(block: (T) -> R): RequestResponse<R> {
    return when(this) {
        is RequestResponse.Success -> {
            RequestResponse.Success(block(data))
        }
        is RequestResponse.Error -> {
            this
        }
    }
}