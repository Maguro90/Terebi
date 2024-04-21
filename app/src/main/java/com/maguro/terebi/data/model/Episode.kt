package com.maguro.terebi.data.model

data class Episode(
    val id: Id,
    val name: String? = null,
    val season: Int? = null,
    val number: Int? = null,
    val summary: String? = null
)
