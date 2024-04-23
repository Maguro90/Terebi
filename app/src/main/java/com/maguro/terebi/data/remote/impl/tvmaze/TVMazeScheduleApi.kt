package com.maguro.terebi.data.remote.impl.tvmaze

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.data.remote.ScheduleApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitScheduleApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.wrapResponse
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDate
import java.time.format.DateTimeFormatter


private val scheduleCache = mutableMapOf<Id, ScheduleItem>()

class TVMazeScheduleApi(
    private val scheduleApi: RetrofitScheduleApi,
) : ScheduleApi {

    private val cacheMutex = Mutex()

    override suspend fun getSchedule(
        date: LocalDate,
        countryCode: String
    ): RequestResponse<List<ScheduleItem>> {

        return wrapResponse {
            cacheMutex.withLock {
                scheduleCache.clear()
            }

            scheduleApi.getSchedule(
                date = date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                countryCode = countryCode
            ).map {
                it.getScheduleItemModel().also {
                    cacheMutex.withLock {
                        scheduleCache[it.id] = it
                    }
                }
            }
        }

    }

    override suspend fun getScheduleItemDetails(
        scheduleItemId: Id
    ): RequestResponse<ScheduleItem?> {
        return cacheMutex.withLock {
            RequestResponse.Success(scheduleCache[scheduleItemId])
        }
    }

}
