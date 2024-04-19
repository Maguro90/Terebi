package com.maguro.terebi.data.model

import java.net.URL

data class Channel(
    val id: Id,
    val name: String,
    val webSite: URL?
)
