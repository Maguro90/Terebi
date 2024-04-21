package com.maguro.terebi.di

import com.maguro.terebi.domain.GetGroupedScheduleUseCase
import com.maguro.terebi.domain.GetGroupedScheduleUseCaseImpl
import com.maguro.terebi.domain.GetShowWithEpisodeUseCase
import com.maguro.terebi.domain.GetShowWithEpisodeUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<GetGroupedScheduleUseCase> {
        GetGroupedScheduleUseCaseImpl(
            scheduleRepository = get(),
            defaultDispatcher = get(DefaultDispatcher)
        )
    }

    factory<GetShowWithEpisodeUseCase> {
        GetShowWithEpisodeUseCaseImpl(
            showRepository = get(),
            episodeRepository = get()
        )
    }

}