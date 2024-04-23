package com.maguro.terebi.data.remote.impl.tvmaze

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Episode
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.remote.EpisodeApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitEpisodeApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.wrapResponse

class TVMazeEpisodeApi(
    private val episodeApi: RetrofitEpisodeApi
) : EpisodeApi {

    override suspend fun getEpisodeDetails(episodeId: Id): RequestResponse<Episode> {
        return wrapResponse {
            episodeApi.getEpisodeDetails(
                episodeId = episodeId.toString()
            ).toEpisodeModel()
        }
    }

}