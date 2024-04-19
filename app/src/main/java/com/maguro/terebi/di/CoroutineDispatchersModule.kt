package com.maguro.terebi.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val IODispatcher = named("Dispatchers.IO")
val DefaultDispatcher = named("Dispatchers.Default")
val MainDispatcher = named("Dispatchers.Main")

val coroutineDispatchersModule = module {

    factory(IODispatcher) { Dispatchers.IO }
    factory(DefaultDispatcher) { Dispatchers.Default }
    factory(MainDispatcher) { Dispatchers.Main }

}
