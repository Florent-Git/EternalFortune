package com.mineinabyss.bonfire.extensions.exposed

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mineinabyss.eternalfortune.model.data.ItemStackList
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.VarCharColumnType
import java.lang.reflect.Type

class ItemStackListColumnType : JsonColumnType<ItemStackList>(ItemStackList::class.java) {
    override fun validateValueBeforeUpdate(value: ConfigurationSerializable?) {
        require(value is ItemStackList) {
            "Value type is not ItemStackList"
        }
    }
}