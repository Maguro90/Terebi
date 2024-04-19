package com.maguro.terebi.data.remote.impl.tvmaze.responses

import com.maguro.terebi.data.model.Channel
import com.maguro.terebi.data.model.LongId
import java.net.URL

data class WebChannelResponse(
    val id: Long,
    val name: String,
    val officialSite: String?
) {

    fun getChannelModel() : Channel {
        return Channel(
            id = LongId(id),
            name = name,
            webSite = officialSite?.let { URL(it) }
        )
    }

}
