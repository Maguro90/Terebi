package com.maguro.terebi.di.remote.tvmaze

import com.maguro.terebi.data.IdDeserializer
import com.maguro.terebi.data.remote.impl.tvmaze.TVMazeIdDeserializer
import com.maguro.terebi.di.remote.ApiRootUrl
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val tvMazeApiModule = module {

    single(ApiRootUrl) { "https://api.tvmaze.com" }

    single<IdDeserializer> { TVMazeIdDeserializer() }

    loadKoinModules(tvMazeRetrofitModule)

}