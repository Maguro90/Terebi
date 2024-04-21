package com.maguro.terebi.data.remote.impl.tvmaze.retrofit

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.model.Show
import com.maguro.terebi.data.remote.ShowApi
import com.maguro.terebi.data.remote.impl.tvmaze.responses.ShowResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class RetrofitShowApi(
    retrofit: Retrofit
) : ShowApi {

    private val retrofitApi = retrofit.create(RetrofitApi::class.java)

    override suspend fun getShowDetails(showId: Id): RequestResponse<Show> {
        return wrapResponse {
            retrofitApi.getShowDetails(
                showId = showId.toString()
            ).getShowModel()
        }
    }

    private interface RetrofitApi {
        @GET("shows/{id}")
        suspend fun getShowDetails(
            @Path("id") showId: String
        ): ShowResponse
    }

}