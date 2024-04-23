package com.maguro.terebi.data.repository

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Id
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

    suspend fun getScheduleItemDetails(
        scheduleItemId: Id
    ): RequestResponse<ScheduleItem?>

}

class ScheduleRepositoryImpl(
    private val scheduleApi: ScheduleApi,
    private val ioDispatcher: CoroutineDispatcher
) : ScheduleRepository {

    override suspend fun getSchedule(
        date: LocalDate,
        countryCode: String
    ): RequestResponse<List<ScheduleItem>> {
        return withContext(ioDispatcher) {
            scheduleApi.getSchedule(
                date = date,
                countryCode = countryCode
            )
        }
    }

    override suspend fun getScheduleItemDetails(scheduleItemId: Id): RequestResponse<ScheduleItem?> {
        return withContext(ioDispatcher) {
            scheduleApi.getScheduleItemDetails(scheduleItemId)
        }
    }

}