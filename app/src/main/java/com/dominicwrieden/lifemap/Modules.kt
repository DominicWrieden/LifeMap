package com.dominicwrieden.lifemap

import com.dominicwrieden.lifemap.feature.login.viewmodel.LoginViewModel
import com.dominicwrieden.lifemap.feature.main.viewmodel.MainViewModel
import com.dominicwrieden.lifemap.usecase.area.DownloadAreasUseCase
import com.dominicwrieden.lifemap.usecase.area.DownloadAreasUseCaseImpl
import com.dominicwrieden.lifemap.usecase.authentication.GetLoggedInUserUseCase
import com.dominicwrieden.lifemap.usecase.authentication.GetLoggedInUserUseCaseImpl
import com.dominicwrieden.lifemap.usecase.authentication.LoginUseCase
import com.dominicwrieden.lifemap.usecase.authentication.LoginUseCaseImpl
import com.dominicwrieden.lifemap.usecase.item.DownloadItemsUseCase
import com.dominicwrieden.lifemap.usecase.item.DownloadItemsUseCaseImpl
import com.dominicwrieden.lifemap.usecase.itemtype.DownloadItemTypesUseCase
import com.dominicwrieden.lifemap.usecase.itemtype.DownloadItemTypesUseCaseImpl
import com.dominicwrieden.lifemap.usecase.propertyconfig.DownloadPropertyConfigsUseCase
import com.dominicwrieden.lifemap.usecase.propertyconfig.DownloadPropertyConfigsUseCaseImpl
import com.dominicwrieden.lifemap.usecase.state.DownloadStatesUseCase
import com.dominicwrieden.lifemap.usecase.state.DownloadStatesUseCaseImpl
import com.dominicwrieden.lifemap.usecase.user.DownloadUsersUseCase
import com.dominicwrieden.lifemap.usecase.user.DownloadUsersUseCaseImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {



    single<LoginUseCase>{ LoginUseCaseImpl(get())}
    single<GetLoggedInUserUseCase>{GetLoggedInUserUseCaseImpl(get())}
    single<DownloadAreasUseCase>{DownloadAreasUseCaseImpl(get())}
    single<DownloadUsersUseCase>{ DownloadUsersUseCaseImpl(get()) }
    single<DownloadStatesUseCase> { DownloadStatesUseCaseImpl(get())  }
    single<DownloadPropertyConfigsUseCase>{DownloadPropertyConfigsUseCaseImpl(get())}
    single<DownloadItemTypesUseCase>{DownloadItemTypesUseCaseImpl(get())}
    single<DownloadItemsUseCase>{DownloadItemsUseCaseImpl(get(),get())}


    viewModel { LoginViewModel(get(),get(),get(),get(),get(),get(),get()) }
    viewModel { MainViewModel(get()) }
}