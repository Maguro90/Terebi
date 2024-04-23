package com.maguro.terebi.domain

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.model.ScheduleItem
import com.maguro.terebi.data.remote.ScheduleApi

interface GetScheduleItemDetailsUseCase {
    suspend operator fun invoke(scheduleItemId: Id): RequestResponse<ScheduleItem?>

}

class GetScheduleItemDetailsUseCaseImpl(
    private val scheduleApi: ScheduleApi
): GetScheduleItemDetailsUseCase {

    override suspend fun invoke(scheduleItemId: Id): RequestResponse<ScheduleItem?> {
        return scheduleApi.getScheduleItemDetails(scheduleItemId)
    }

}