package com.maguro.terebi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface Id

@JvmInline
@Parcelize
value class LongId(private val value: Long): Id, Parcelable {
    override fun toString(): String {
        return value.toString()
    }
}