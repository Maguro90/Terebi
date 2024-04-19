package com.maguro.terebi.di

import com.maguro.terebi.data.repository.ScheduleRepository
import com.maguro.terebi.data.repository.ScheduleRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<ScheduleRepository> {
        ScheduleRepositoryImpl(
            scheduleApi = get(),
            coroutineDispatcher = get(IODispatcher)
        )
    }

}