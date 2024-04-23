package com.maguro.terebi.data.remote.impl.tvmaze

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.model.Show
import com.maguro.terebi.data.remote.ShowApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitShowApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.wrapResponse

class TVMazeShowApi(
    private val showApi: RetrofitShowApi
) : ShowApi {

    override suspend fun getShowDetails(showId: Id): RequestResponse<Show> {
        return wrapResponse {
            showApi.getShowDetails(
                showId = showId.toString()
            ).getShowModel()
        }
    }

    override suspend fun searchShows(query: String): RequestResponse<List<Show>> {
        return wrapResponse {
            showApi.searchShows(
                query = query
            ).map {
                it.show.getShowModel()
            }
        }
    }

}