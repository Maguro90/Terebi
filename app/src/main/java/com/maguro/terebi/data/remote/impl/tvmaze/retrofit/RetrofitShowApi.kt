package com.maguro.terebi.data.remote.impl.tvmaze.retrofit

import com.maguro.terebi.data.remote.impl.tvmaze.responses.ShowResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitShowApi {

    @GET("shows/{id}")
    suspend fun getShowDetails(
        @Path("id") showId: String
    ): ShowResponse

}