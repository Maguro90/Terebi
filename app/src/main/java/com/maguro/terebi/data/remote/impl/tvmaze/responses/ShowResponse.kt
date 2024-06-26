package com.maguro.terebi.data.remote.impl.tvmaze.responses

import com.maguro.terebi.data.model.Channel
import com.maguro.terebi.data.model.LongId
import com.maguro.terebi.data.model.Show
import com.squareup.moshi.Json
import java.net.URL

data class ShowResponse(
    val id: Long,
    val name: String,
    val image: ImageResponse? = null,
    val genres: List<String>? = null,
    val rating: RatingResponse,
    val summary: String? = null,
    val network: NetworkResponse? = null,
    val webChannel: WebChannelResponse? = null,
    @Json(name = "_links")
    val links: ShowLinks
) {

    private fun getImage(): URL? {
        return image?.medium?.let { URL(image.original) }
    }

    fun getChannelModel() : Channel {
        return network?.getChannelModel()
            ?: webChannel?.getChannelModel()
            ?: throw IllegalStateException("No channel defined for show $name")
    }

    fun getShowModel(): Show {
        return Show(
            id = LongId(id),
            name = name,
            image = getImage(),
            genres = genres?.takeIf { it.isNotEmpty() },
            rating = rating.getRatingModel(),
            summary = summary
        )
    }

}

data class ShowLinks(
    val self: LinkResponse,
    @Json(name = "previousepisode")
    val previousEpisode: LinkResponse,
    @Json(name = "nextepisode")
    val nextEpisode: LinkResponse,
)
