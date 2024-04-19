package com.maguro.terebi.data.repository

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.data.remote.ScheduleApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate

interface ScheduleRepository {

    suspend fun getSchedule(
        date: LocalDate,
        countryCode: String
    ): RequestResponse<List<ScheduleItem>>

}

class ScheduleRepositoryImpl(
    private val scheduleApi: ScheduleApi,
    private val coroutineDispatcher: CoroutineDispatcher
) : ScheduleRepository {

    override suspend fun getSchedule(
        date: LocalDate,
        countryCode: String
    ): RequestResponse<List<ScheduleItem>> {
        return withContext(coroutineDispatcher) {
            scheduleApi.getSchedule(
                date = date,
                countryCode = countryCode
            )
        }
    }

}