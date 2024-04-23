package com.maguro.terebi.di

import com.maguro.terebi.domain.GetGroupedScheduleUseCase
import com.maguro.terebi.domain.GetGroupedScheduleUseCaseImpl
import com.maguro.terebi.domain.GetScheduleItemDetailsUseCase
import com.maguro.terebi.domain.GetScheduleItemDetailsUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<GetGroupedScheduleUseCase> {
        GetGroupedScheduleUseCaseImpl(
            scheduleRepository = get(),
            defaultDispatcher = get(DefaultDispatcher)
        )
    }

    factory<GetScheduleItemDetailsUseCase> {
        GetScheduleItemDetailsUseCaseImpl(
            scheduleApi = get()
        )
    }

}