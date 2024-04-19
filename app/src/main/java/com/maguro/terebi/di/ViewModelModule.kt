package com.maguro.terebi.di

import com.maguro.terebi.ui.screens.schedule.ScheduleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ScheduleViewModel(get()) }

}