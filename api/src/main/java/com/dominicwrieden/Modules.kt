package com.dominicwrieden

import com.dominicwrieden.api.BuildConfig
import com.dominicwrieden.api.`interface`.Api
import com.dominicwrieden.api.implementation.old.ApiImpl
import com.dominicwrieden.api.implementation.old.authentication.AuthenticationInterceptor
import com.dominicwrieden.api.implementation.old.authentication.AuthenticationManager
import com.dominicwrieden.api.implementation.old.authentication.AuthenticationService
import com.dominicwrieden.api.implementation.old.authentication.source.local.AuthenticationSharedPreferences
import com.dominicwrieden.api.implementation.old.authentication.source.retrofit.AuthenticationApi
import com.dominicwrieden.api.implementation.old.content.ContentService
import com.dominicwrieden.api.implementation.old.content.retrofit.ContentApi
import com.dominicwrieden.api.util.MoshiAdapters
import com.oussaki.rxfilesdownloader.RxDownloader
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val oldApiModule = module {

    single { AuthenticationSharedPreferences(androidContext()) }
    single { AuthenticationManager(get()) }

    single {
        Moshi.Builder()
            .add(MoshiAdapters)
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(get(),get()))
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS) //TODO refactoring: outsource this constant
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    factory { RxDownloader.Builder(androidContext())
        .client(get())}

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single { AuthenticationService(get<Retrofit>().create(AuthenticationApi::class.java), get()) }
    single { ContentService(androidContext(),get<Retrofit>().create(ContentApi::class.java), get(),get()) }


    single { ApiImpl(get(), get(), get()) } bind Api::class
}
