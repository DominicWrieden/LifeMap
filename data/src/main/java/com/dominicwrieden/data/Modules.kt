package com.dominicwrieden.data

import com.dominicwrieden.*
import com.dominicwrieden.data.repository.`interface`.*
import com.dominicwrieden.data.repository.implementation.*
import com.dominicwrieden.util.sqldelight.LocationColumnAdapter
import com.dominicwrieden.util.sqldelight.OffsetDateTimeColumnAdapter
import com.dominicwrieden.util.sqldelight.PropertyTypeColumnAdapter
import com.dominicwrieden.util.sqldelight.UriColumnAdapter
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

    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get())  }

    single<UserRepository> { UserRepositoryImpl(get(),get()) }

    single<AreaRepository> { AreaRepositoryImpl(get(),get()) }

    single<ItemRepository> { ItemRepositoryImpl(get(),get()) }

    single<StateRepository> { StateRepositoryImpl(get(),get()) }

    single<ItemTypeRepository> { ItemTypeRepositoryImpl(get(),get())  }

    single<PropertyConfigRepository> { PropertyConfigRepositoryImpl(get(),get())  }
}