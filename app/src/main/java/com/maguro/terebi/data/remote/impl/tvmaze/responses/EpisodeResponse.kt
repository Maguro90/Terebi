package com.maguro.terebi.data.remote.impl.tvmaze.responses

import com.maguro.terebi.data.model.Episode
import com.maguro.terebi.data.model.LongId
import com.squareup.moshi.Json

data class EpisodeResponse(
    val id: Long,
    val name: String,
    val season: Int? = null,
    val number: Int? = null,
    val summary: String? = null,
    @Json(name = "_links")
    val links: EpisodeLinks
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

data class EpisodeLinks(
    val self: LinkResponse,
    val show: LinkResponse
)
