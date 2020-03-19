package com.dominicwrieden.data.model

enum class PropertyType{
    STRING,
    INT,
    DATE
}

data class PropertyConfig(
     val remoteId: String,
     val name: String,
     val description:String?,
     val propertyType: PropertyType
)


/*
interface PropertyConfig<T> {
    val id: Int
    val name: String
    val description: String?
    val value: T
}


data class PropertyConfigString(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val name: String,
    override val description:String?,
    override val value: String
): PropertyConfig<String>

data class PropertyConfigInt(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val name: String,
    override val description:String?,
    override va

): PropertyConfig<Int> */
