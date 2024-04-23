package com.maguro.terebi.data.remote.impl.tvmaze.retrofit

import com.maguro.terebi.data.remote.impl.tvmaze.responses.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitEpisodeApi {

    @GET("episodes/{id}")
    suspend fun getEpisodeDetails(
        @Path("id") episodeId: String
    ): EpisodeResponse

}