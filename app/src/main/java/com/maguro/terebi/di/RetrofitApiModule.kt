package com.maguro.terebi.di

import com.maguro.terebi.data.remote.ScheduleApi
import com.maguro.terebi.data.remote.impl.tvmaze.retrofit.RetrofitScheduleApi
import org.koin.dsl.module

val retrofitApiModule = module {

    factory<ScheduleApi> {
        RetrofitScheduleApi(get())
    }

}