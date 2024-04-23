package com.maguro.terebi.di.remote.tvmaze

import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitEpisodeApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitScheduleApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitShowApi
import com.maguro.terebi.di.remote.retrofitModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit


val tvMazeRetrofitModule = module {

    factory {
        get<Retrofit>().create(RetrofitScheduleApi::class.java)
    }

    factory {
        get<Retrofit>().create(RetrofitShowApi::class.java)
    }

    factory {
        get<Retrofit>().create(RetrofitEpisodeApi::class.java)
    }

    loadKoinModules(retrofitModule)

}