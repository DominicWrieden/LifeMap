package com.dominicwrieden.data

import com.dominicwrieden.data.repository.`interface`.UserRepository
import com.dominicwrieden.data.repository.implementation.UserRepositoryImpl
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        AndroidSqliteDriver(schema = Database.Schema, context = androidContext(), name = "lifemap.db")
    }

    single<UserRepository>{
        UserRepositoryImpl(get())
    }


}