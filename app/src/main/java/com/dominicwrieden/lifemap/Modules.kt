package com.dominicwrieden.lifemap

import com.dominicwrieden.lifemap.feature.login.viewmodel.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {
    viewModel { LoginViewModel() }
}