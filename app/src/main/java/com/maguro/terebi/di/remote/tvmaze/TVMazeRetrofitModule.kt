package com.maguro.terebi.di.remote.tvmaze

import com.maguro.terebi.data.remote.EpisodeApi
import com.maguro.terebi.data.remote.ScheduleApi
import com.maguro.terebi.data.remote.ShowApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitEpisodeApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitScheduleApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitShowApi
import com.maguro.terebi.di.remote.retrofitModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


val tvMazeRetrofitModule = module {

    factory<ScheduleApi> {
        RetrofitScheduleApi(get())
    }

    factory<ShowApi> {
        RetrofitShowApi(get())
    }

    factory<EpisodeApi> {
        RetrofitEpisodeApi(get())
    }

    loadKoinModules(retrofitModule)

}