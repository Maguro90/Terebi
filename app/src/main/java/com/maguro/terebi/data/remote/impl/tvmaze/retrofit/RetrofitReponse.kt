package com.maguro.terebi.data.remote.impl.tvmaze.retrofit

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.remote.RemoteRequestError
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> wrapResponse(block: suspend () -> T): RequestResponse<T> {
    return try {
        RequestResponse.Success(data = block())
    } catch (e: UnknownHostException) {
        RemoteRequestError.NoConnection(cause = e)
    } catch (e: SocketTimeoutException) {
        RemoteRequestError.ConnectionTimeout(cause = e)
    } catch (e: HttpException) {
        RemoteRequestError.Server(code = e.code(), cause = e)
    } catch (e: Throwable) {
        RemoteRequestError.Unknown(cause = e)
    }
}