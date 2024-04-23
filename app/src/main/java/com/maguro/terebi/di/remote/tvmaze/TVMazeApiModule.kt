package com.maguro.terebi.di.remote.tvmaze

import com.maguro.terebi.data.IdDeserializer
import com.maguro.terebi.data.remote.EpisodeApi
import com.maguro.terebi.data.remote.ScheduleApi
import com.maguro.terebi.data.remote.ShowApi
import com.maguro.terebi.data.remote.impl.tvmaze.TVMazeEpisodeApi
import com.maguro.terebi.data.remote.impl.tvmaze.TVMazeIdDeserializer
import com.maguro.terebi.data.remote.impl.tvmaze.TVMazeScheduleApi
import com.maguro.terebi.data.remote.impl.tvmaze.TVMazeShowApi
import com.maguro.terebi.di.remote.ApiRootUrl
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val tvMazeApiModule = module {

    single(ApiRootUrl) { "https://api.tvmaze.com" }

    single<IdDeserializer> { TVMazeIdDeserializer() }

    factory<ScheduleApi> {
        TVMazeScheduleApi(get())
    }

    factory<ShowApi> {
        TVMazeShowApi(get())
    }

    factory<EpisodeApi> {
        TVMazeEpisodeApi(get())
    }

    loadKoinModules(tvMazeRetrofitModule)

}