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
import com.dominicwrieden.data.util.FileManager
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

    single<FileManager>{
        FileManager(androidContext())
    }

    single<AuthenticationRepository> { AuthenticationRepositoryImpl()}

    single<UserRepository> { UserRepositoryImpl() }

    single<AreaRepository> { AreaRepositoryImpl() }

    single<ItemRepository> { ItemRepositoryImpl() }

    single<StateRepository> { StateRepositoryImpl() }

    single<ItemTypeRepository> { ItemTypeRepositoryImpl() }

    single<PropertyConfigRepository> { PropertyConfigRepositoryImpl() }
}