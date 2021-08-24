package com.mineinabyss.bonfire.extensions.exposed

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.BasicBinaryColumnType
import org.jetbrains.exposed.sql.VarCharColumnType
import java.lang.reflect.Type

/**
 * Abstraction to store a Map<String, Any> into a JSON string into a DB varchar with the Exposed API. <br>
 * The <code>validateValueBeforeUpdate</code> method is to be defined to check the type beforehand <br>
 *
 * Every type should implement org.bukkit.configuration.ConfigurationSerializable
 *
 * Uses the GSON library
 */
abstract class JsonColumnType<T : ConfigurationSerializable>(private val clazz: Class<T>) : VarCharColumnType(255) {
    private val gson = GsonBuilder()
        .create()

    private val mapStringAnyType: Type = object : TypeToken<Map<String, Any>>() {}.type

    override fun nonNullValueToString(value: Any): String {
        return gson.toJson((value as ConfigurationSerializable).serialize())
    }

    override fun valueToString(value: Any?): String {
        requireNotNull(value) {
            "Value is null"
        }
        return nonNullValueToString(value)
    }

    override fun notNullValueToDB(value: Any): Any {
        return gson.toJson((value as ConfigurationSerializable).serialize())
    }

    override fun valueFromDB(value: Any): Any {
        require(value is String) {
            "Value is required to be String"
        }
        return ConfigurationSerialization.deserializeObject(gson.fromJson(value, mapStringAnyType), clazz) !!
    }

    override fun valueToDB(value: Any?): Any? {
        requireNotNull(value) {
            "Value cannot be null"
        }
        return notNullValueToDB(value)
    }

    final override fun validateValueBeforeUpdate(value: Any?) {
        super.validateValueBeforeUpdate(value)
        require(value is ConfigurationSerializable) {
            "Value's type does not inherit ConfigurationSerializable"
        }
        validateValueBeforeUpdate(value)
    }

    abstract fun validateValueBeforeUpdate(value: ConfigurationSerializable?)
}