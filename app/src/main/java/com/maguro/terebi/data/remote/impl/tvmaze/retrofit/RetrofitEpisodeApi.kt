package com.maguro.terebi.data.remote.impl.tvmaze.retrofit

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Episode
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.remote.EpisodeApi
import com.maguro.terebi.data.remote.impl.tvmaze.responses.EpisodeResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class RetrofitEpisodeApi(
    retrofit: Retrofit
) : EpisodeApi {

    private val retrofitApi = retrofit.create(RetrofitApi::class.java)

    override suspend fun getEpisodeDetails(episodeId: Id): RequestResponse<Episode> {
        return wrapResponse {
            retrofitApi.getEpisodeDetails(
                episodeId = episodeId.toString()
            ).toEpisodeModel()
        }
    }

    private interface RetrofitApi {
        @GET("episodes/{id}")
        fun getEpisodeDetails(
            @Path("id") episodeId: String
        ): EpisodeResponse
    }
}