package com.maguro.terebi.data.remote

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Episode
import com.maguro.terebi.data.model.Id

interface EpisodeApi {

    suspend fun getEpisodeDetails(episodeId: Id): RequestResponse<Episode>

}