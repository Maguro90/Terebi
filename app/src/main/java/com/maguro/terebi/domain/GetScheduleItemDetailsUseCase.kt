package com.maguro.terebi.domain

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.data.repository.ScheduleRepository

interface GetScheduleItemDetailsUseCase {
    suspend operator fun invoke(scheduleItemId: Id): RequestResponse<ScheduleItem?>

}

class GetScheduleItemDetailsUseCaseImpl(
    private val scheduleRepository: ScheduleRepository
): GetScheduleItemDetailsUseCase {

    override suspend fun invoke(scheduleItemId: Id): RequestResponse<ScheduleItem?> {
        return scheduleRepository.getScheduleItemDetails(scheduleItemId)
    }

}