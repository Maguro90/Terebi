package com.maguro.terebi.data.remote

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.model.Show

interface ShowApi {

    suspend fun getShowDetails(showId: Id): RequestResponse<Show>

}