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
import com.squareup.moshi.Moshi
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val oldApiModule = module {

    single { AuthenticationSharedPreferences(androidContext()) }
    single { AuthenticationManager() }

    single {
        Moshi.Builder()
            .add(MoshiAdapters)
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor())
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS) //TODO refactoring: outsource this constant
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(AuthenticationApi::class.java) }
    single { get<Retrofit>().create(ContentApi::class.java) }

    single { AuthenticationService() }
    single { ContentService() }


    single { ApiImpl() } bind Api::class
}
