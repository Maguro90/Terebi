package com.maguro.terebi.data.remote.impl.tvmaze.responses

import com.maguro.terebi.data.model.Episode
import com.maguro.terebi.data.model.LongId

data class EpisodeResponse(
    val id: Long,
    val name: String,
    val season: Int?,
    val number: Int?,
    val summary: String? = null
) {

    fun toEpisodeModel(): Episode {
        return Episode(
            id = LongId(id),
            name = name,
            season = season,
            number = number,
            summary = summary
        )
    }

}
