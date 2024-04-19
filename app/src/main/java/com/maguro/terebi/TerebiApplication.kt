package com.maguro.terebi

import android.app.Application
import com.maguro.terebi.di.coroutineDispatchersModule
import com.maguro.terebi.di.repositoryModule
import com.maguro.terebi.di.retrofitApiModule
import com.maguro.terebi.di.retrofitModule
import com.maguro.terebi.di.useCaseModule
import com.maguro.terebi.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TerebiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TerebiApplication)
            modules(
                coroutineDispatchersModule,
                retrofitModule,
                retrofitApiModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }

    }

}