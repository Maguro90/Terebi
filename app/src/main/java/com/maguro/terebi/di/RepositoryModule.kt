package com.maguro.terebi.di

import com.maguro.terebi.data.repository.EpisodeRepository
import com.maguro.terebi.data.repository.EpisodeRepositoryImpl
import com.maguro.terebi.data.repository.ScheduleRepository
import com.maguro.terebi.data.repository.ScheduleRepositoryImpl
import com.maguro.terebi.data.repository.ShowRepository
import com.maguro.terebi.data.repository.ShowRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<ScheduleRepository> {
        ScheduleRepositoryImpl(
            scheduleApi = get(),
            ioDispatcher = get(IODispatcher)
        )
    }

    factory<ShowRepository> {
        ShowRepositoryImpl(
            showApi = get(),
            ioDispatcher = get(IODispatcher)
        )
    }

    factory<EpisodeRepository> {
        EpisodeRepositoryImpl(
            episodeApi = get(),
            ioDispatcher = get(IODispatcher)
        )
    }
}