package com.maguro.terebi.data.remote

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.ScheduleItem
import java.time.LocalDate

interface ScheduleApi {
    suspend fun getSchedule(
        date: LocalDate,
        countryCode: String
    ): RequestResponse<List<ScheduleItem>>

}