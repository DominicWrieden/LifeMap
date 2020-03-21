package com.dominicwrieden.lifemap

import com.dominicwrieden.lifemap.feature.login.viewmodel.LoginViewModel
import com.dominicwrieden.lifemap.usecase.DownloadUseCase
import com.dominicwrieden.lifemap.usecase.LoginUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get(),get()) }


    single{ DownloadUseCase(get(),get(),get(),get(),get(),get()) }

    single {LoginUseCase(get()) }
}