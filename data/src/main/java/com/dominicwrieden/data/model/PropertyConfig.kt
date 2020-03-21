package com.dominicwrieden.data.model

enum class PropertyType{
    STRING,
    INT,
    DATE
}


/**
 * Property* model, which is corresponding to the database structure.
 * Will be used for the communication between the data module {@link com.dominicwrieden.data}
 * and the api module {@link com.dominicwrieden.api}
 *
 * This represents a configured String|Int|Date property from the server. Will be used for the
 * Properties {@link com.dominicwrieden.data.model.Property} for an
 * assessment {@link com.dominicwrieden.data.model.ItemEntry} of an Item {@link com.dominicwrieden.data.model.Item}
 *
 * IMPORTANT: Do not use this model for the repositories, which are communicating with the app module
 */
data class PropertyConfig(
     val remoteId: String,
     val name: String,
     val description:String?,
     val propertyType: PropertyType
)