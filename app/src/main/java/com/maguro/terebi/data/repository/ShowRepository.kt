package com.maguro.terebi.data.repository

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.model.Show
import com.maguro.terebi.data.remote.ShowApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface ShowRepository {

    suspend fun getShowDetails(showId: Id): RequestResponse<Show>

}

class ShowRepositoryImpl(
    private val showApi: ShowApi,
    private val ioDispatcher: CoroutineDispatcher
): ShowRepository {

    override suspend fun getShowDetails(showId: Id): RequestResponse<Show> {
        return withContext(ioDispatcher) {
            showApi.getShowDetails(showId)
        }
    }

}