package com.maguro.terebi.data.model

import java.time.OffsetDateTime

data class ScheduleItem(
    val id: Id,
    val show: Show,
    val episode: Episode,
    val channel: Channel,
    val airingDateTime: OffsetDateTime
)
