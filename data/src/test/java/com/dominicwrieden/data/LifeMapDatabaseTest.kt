package com.dominicwrieden.data

import com.dominicwrieden.*
import com.dominicwrieden.data.util.sqldelight.LocationColumnAdapter
import com.dominicwrieden.data.util.sqldelight.OffsetDateTimeColumnAdapter
import com.dominicwrieden.data.util.sqldelight.PropertyTypeColumnAdapter
import com.dominicwrieden.data.util.sqldelight.UriColumnAdapter
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.Assert
import org.junit.Test

class LifeMapDatabaseTest {

    /**
     * Creates a database in memory, which will be freshly created for this class
     */
    private val inMemorySqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        Database.Schema.create(this)
    }

    private val testDatabase = Database(
        driver = inMemorySqlDriver,
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

    @Test
    fun exampleTestUser() {

    }
}
