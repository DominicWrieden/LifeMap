package com.dominicwrieden.data

import com.dominicwrieden.*
import com.dominicwrieden.data.repository.area.AreaRepository
import com.dominicwrieden.data.repository.area.AreaRepositoryImpl
import com.dominicwrieden.data.repository.authentication.AuthenticationRepository
import com.dominicwrieden.data.repository.authentication.AuthenticationRepositoryImpl
import com.dominicwrieden.data.repository.item.ItemRepository
import com.dominicwrieden.data.repository.item.ItemRepositoryImpl
import com.dominicwrieden.data.repository.itemtype.ItemTypeRepository
import com.dominicwrieden.data.repository.itemtype.ItemTypeRepositoryImpl
import com.dominicwrieden.data.repository.propertyconfig.PropertyConfigRepository
import com.dominicwrieden.data.repository.propertyconfig.PropertyConfigRepositoryImpl
import com.dominicwrieden.data.repository.state.StateRepository
import com.dominicwrieden.data.repository.state.StateRepositoryImpl
import com.dominicwrieden.data.repository.user.UserRepository
import com.dominicwrieden.data.repository.user.UserRepositoryImpl
import com.dominicwrieden.data.util.SharedPreferencesUtil
import com.dominicwrieden.data.util.sqldelight.LocationColumnAdapter
import com.dominicwrieden.data.util.sqldelight.OffsetDateTimeColumnAdapter
import com.dominicwrieden.data.util.sqldelight.PropertyTypeColumnAdapter
import com.dominicwrieden.data.util.sqldelight.UriColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = Database.Schema,
            context = androidContext(),
            name = "lifemap.db"
        )
    }

    single {
        Database(
            driver = get(),
            areaAdapter = Area.Adapter(
                geoDbCreateDateAdapter = OffsetDateTimeColumnAdapter()

            ),
            itemAdapter = Item.Adapter(
                createDateAdapter = OffsetDateTimeColumnAdapter()
            ),
            itemEntryAdapter = ItemEntry.Adapter(
                createDateAdapter = OffsetDateTimeColumnAdapter(),
                locationAdapter = LocationColumnAdapter()
            ),
            photoAdapter = Photo.Adapter(
                filePathAdapter = UriColumnAdapter()
            ),
            propertyConfigAdapter = PropertyConfig.Adapter(
                propertyTypeAdapter = PropertyTypeColumnAdapter()
            )
        ).lifeMapDatabaseQueries
    }

    single<SharedPreferencesUtil> {
        SharedPreferencesUtil(androidContext())
    }

    single<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            get(),
            get(),
            get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            get(),
            get()
        )
    }

    single<AreaRepository> {
        AreaRepositoryImpl(
            get(),
            get()
        )
    }

    single<ItemRepository> {
        ItemRepositoryImpl(
            get(),
            get()
        )
    }

    single<StateRepository> {
        StateRepositoryImpl(
            get(),
            get()
        )
    }

    single<ItemTypeRepository> {
        ItemTypeRepositoryImpl(
            get(),
            get()
        )
    }

    single<PropertyConfigRepository> {
        PropertyConfigRepositoryImpl(
            get(),
            get()
        )
    }
}