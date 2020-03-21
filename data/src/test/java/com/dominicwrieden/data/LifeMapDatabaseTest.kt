package com.dominicwrieden.data

import com.dominicwrieden.*
import com.dominicwrieden.util.sqldelight.LocationColumnAdapter
import com.dominicwrieden.util.sqldelight.OffsetDateTimeColumnAdapter
import com.dominicwrieden.util.sqldelight.PropertyTypeColumnAdapter
import com.dominicwrieden.util.sqldelight.UriColumnAdapter
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
        testDatabase.insertUser("u1", "email1", "firstName1", "lasName1")
        testDatabase.insertUser("u2", "email2", "firstName2", "lasName2")
        testDatabase.insertUser("u3", "email3", "firstName3", "lasName3")
        testDatabase.insertUser("u4", "email4", "firstName4", "lasName4")
        testDatabase.insertUser("u5", "email5", "firstName5", "lasName5")


        val users = testDatabase.getAllUser().executeAsList()

        Assert.assertEquals(5, users.size)
    }
}
