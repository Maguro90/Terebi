package com.maguro.terebi.data.remote.impl.tvmaze.retrofit

import com.maguro.terebi.data.remote.impl.tvmaze.responses.ShowResponse
import com.maguro.terebi.data.remote.impl.tvmaze.responses.ShowSearchResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitShowApi {

    @GET("shows/{id}")
    suspend fun getShowDetails(
        @Path("id") showId: String
    ): ShowResponse

    @GET("search/shows")
    suspend fun searchShows(
        @Query("q") query: String
    ): List<ShowSearchResponseItem>

}