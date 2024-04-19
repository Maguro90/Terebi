package com.maguro.terebi.di

import com.maguro.terebi.domain.GetGroupedScheduleUseCase
import com.maguro.terebi.domain.GetGroupedScheduleUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<GetGroupedScheduleUseCase> {
        GetGroupedScheduleUseCaseImpl(
            scheduleRepository = get(),
            coroutineDispatcher = get(DefaultDispatcher)
        )
    }

}