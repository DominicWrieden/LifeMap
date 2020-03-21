package com.dominicwrieden

import com.dominicwrieden.api.BuildConfig
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.implementation.old.ApiImpl
import com.dominicwrieden.api.implementation.old.authentication.LoginService
import com.dominicwrieden.api.implementation.old.authentication.retrofit.LoginApi
import com.dominicwrieden.api.implementation.old.content.DownloadService
import com.dominicwrieden.api.implementation.old.content.retrofit.DownloadApi
import com.dominicwrieden.api.util.MoshiAdapters
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


val oldApiModule = module {

    single {
        Moshi.Builder()
            .add(MoshiAdapters)
            .build()
    }

    single {
        OkHttpClient.Builder()
            //.addInterceptor(get<AuthenticationInterceptor>()) //TODO AuthenticationInterceptor, für Tokenablauf
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single { LoginService(get<Retrofit>().create(LoginApi::class.java), androidContext()) }
    single {DownloadService(get<Retrofit>().create(DownloadApi::class.java), get())}


    single { ApiImpl(get(),get()) } bind Api::class

}