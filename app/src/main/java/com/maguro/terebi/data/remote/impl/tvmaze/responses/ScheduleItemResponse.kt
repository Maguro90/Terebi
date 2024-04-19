package com.maguro.terebi.data.remote.impl.tvmaze.responses

import com.maguro.terebi.data.model.Episode
import com.maguro.terebi.data.model.LongId
import com.maguro.terebi.data.model.ScheduleItem
import java.time.OffsetDateTime

data class ScheduleItemResponse(
    val id: Long,
    val name: String,
    val season: Int?,
    val number: Int?,
    val airstamp: String,
    val show: ShowResponse,
) {

    private fun getEpisodeModel(): Episode? {
        return Episode(
            id = LongId(id),
            name = name,
            season = season ?: return null,
            number = number ?: return null
        )
    }

    fun getScheduleItemModel(): ScheduleItem {
        return ScheduleItem(
            show = show.getShowModel(),
            episode = getEpisodeModel(),
            channel = show.getChannelModel(),
            airingDateTime = OffsetDateTime.parse(airstamp)
        )
    }
}
