package com.maguro.terebi.data.remote.impl.tvmaze.responses

import kotlin.math.roundToInt

data class RatingResponse(
    val average: Float? = null
) {

    fun getRatingModel(): Int? {
        return ((average ?: return null) / 2).roundToInt()
    }

}
