package com.maguro.terebi.domain

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.map
import com.maguro.terebi.data.model.Channel
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.data.repository.ScheduleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate

interface GetGroupedScheduleUseCase {

    suspend operator fun invoke(
        date: LocalDate,
        countryCode: String
    ): RequestResponse<Map<Channel, List<ScheduleItem>>>

}

class GetGroupedScheduleUseCaseImpl(
    private val scheduleRepository: ScheduleRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : GetGroupedScheduleUseCase {

    override suspend fun invoke(
        date: LocalDate,
        countryCode: String
    ): RequestResponse<Map<Channel, List<ScheduleItem>>> {
        return scheduleRepository.getSchedule(date, countryCode)
            .map {
                withContext(coroutineDispatcher) {
                    it.sortedBy { it.airingDateTime }
                    .groupBy { it.channel }
                }
            }
    }

}