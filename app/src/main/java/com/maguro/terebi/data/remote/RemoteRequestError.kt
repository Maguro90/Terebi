package com.maguro.terebi.data.remote

import com.maguro.terebi.data.RequestResponse

sealed interface RemoteRequestError : RequestResponse.Error {

    data class ConnectionTimeout(
        override val cause: Throwable? = null
    ) : RemoteRequestError

    data class NoConnection(
        override val cause: Throwable? = null
    ): RemoteRequestError

    data class Server(
        val code: Int,
        override val cause: Throwable? = null
    ): RemoteRequestError

    data class Unknown(
        override val cause: Throwable? = null
    ): RemoteRequestError
}