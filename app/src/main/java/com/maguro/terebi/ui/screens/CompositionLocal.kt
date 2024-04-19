package com.maguro.terebi.ui.screens

import androidx.compose.runtime.compositionLocalOf
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

val LocalSystemTimeOffset = compositionLocalOf {
    ZoneId.systemDefault().rules.getOffset(Instant.now())
}

val LocalSystemTimeFormat = compositionLocalOf {
    DateTimeFormatter
        .ofLocalizedTime(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
}