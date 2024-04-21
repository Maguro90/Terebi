package com.maguro.terebi.data.repository

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Episode
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.remote.EpisodeApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface EpisodeRepository {

    suspend fun getEpisodeDetails(episodeId: Id): RequestResponse<Episode>

}

class EpisodeRepositoryImpl(
    private val episodeApi: EpisodeApi,
    private val ioDispatcher: CoroutineDispatcher
): EpisodeRepository {

    override suspend fun getEpisodeDetails(episodeId: Id): RequestResponse<Episode> {
        return withContext(ioDispatcher) {
            episodeApi.getEpisodeDetails(episodeId)
        }
    }

}