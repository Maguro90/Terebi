package com.maguro.terebi.data.model

import java.net.URL

data class Show(
    val id: Id,
    val name: String,
    val image: URL?,
    val genres: List<String>? = null,
    val rating: Int? = null,
    val summary: String? = null
)
