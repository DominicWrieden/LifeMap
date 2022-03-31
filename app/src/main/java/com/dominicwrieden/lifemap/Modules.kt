package com.dominicwrieden.lifemap

import com.dominicwrieden.lifemap.core.NavigationManager
import com.dominicwrieden.lifemap.feature.login.viewmodel.LoginViewModel
import com.dominicwrieden.lifemap.feature.main.viewmodel.DrawerViewModel
import com.dominicwrieden.lifemap.feature.main.viewmodel.MainViewModel
import com.dominicwrieden.lifemap.feature.map.viewmodel.MapViewModel
import com.dominicwrieden.lifemap.usecase.area.*
import com.dominicwrieden.lifemap.usecase.authentication.GetLoggedInUserUseCase
import com.dominicwrieden.lifemap.usecase.authentication.GetLoggedInUserUseCaseImpl
import com.dominicwrieden.lifemap.usecase.authentication.LoginUseCase
import com.dominicwrieden.lifemap.usecase.authentication.LoginUseCaseImpl
import com.dominicwrieden.lifemap.usecase.item.DownloadItemsUseCase
import com.dominicwrieden.lifemap.usecase.item.DownloadItemsUseCaseImpl
import com.dominicwrieden.lifemap.usecase.item.GetItemsForAreaUseCase
import com.dominicwrieden.lifemap.usecase.item.GetItemsForAreaUseCaseImpl
import com.dominicwrieden.lifemap.usecase.itemtype.DownloadItemTypesUseCase
import com.dominicwrieden.lifemap.usecase.itemtype.DownloadItemTypesUseCaseImpl
import com.dominicwrieden.lifemap.usecase.propertyconfig.DownloadPropertyConfigsUseCase
import com.dominicwrieden.lifemap.usecase.propertyconfig.DownloadPropertyConfigsUseCaseImpl
import com.dominicwrieden.lifemap.usecase.state.DownloadStatesUseCase
import com.dominicwrieden.lifemap.usecase.state.DownloadStatesUseCaseImpl
import com.dominicwrieden.lifemap.usecase.user.DownloadUsersUseCase
import com.dominicwrieden.lifemap.usecase.user.DownloadUsersUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { NavigationManager() }
    single<LoginUseCase> { LoginUseCaseImpl() }
    single<GetLoggedInUserUseCase> { GetLoggedInUserUseCaseImpl() }
    single<DownloadAreasUseCase> { DownloadAreasUseCaseImpl() }
    single<DownloadUsersUseCase> { DownloadUsersUseCaseImpl() }
    single<DownloadStatesUseCase> { DownloadStatesUseCaseImpl() }
    single<DownloadPropertyConfigsUseCase> { DownloadPropertyConfigsUseCaseImpl() }
    single<DownloadItemTypesUseCase> { DownloadItemTypesUseCaseImpl() }
    single<DownloadItemsUseCase> { DownloadItemsUseCaseImpl() }
    single<GetAreaUseCase> { GetAreaUseCaseImpl() }
    single<GetAreasForUserUseCase> { GetAreasForUserUseCaseImpl() }
    single<DownloadGeoDbForAreaUseCase> { DownloadGeoDbForAreaUseCaseImpl() }
    single<GetGeoDbForAreaUseCase> { GetGeoDbForAreaUseCaseImpl() }
    single<GetItemsForAreaUseCase> { GetItemsForAreaUseCaseImpl() }
    single<GetSelectedAreaUseCase> { GetSelectedAreaUseCaseImpl() }
    single<SetSelectedAreaUseCase> { SetSelectedAreaUseCaseImpl() }
    single<SetDefaultAreaUseCase> { SetDefaultAreaUseCaseImpl() }

    viewModel { LoginViewModel() }

    viewModel { MapViewModel() }

    viewModel {MainViewModel() }

    viewModel {DrawerViewModel()}
}