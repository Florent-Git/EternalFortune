package com.mineinabyss.eternalfortune.model.data

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.inventory.ItemStack

class ItemStackList(val itemList: List<ItemStack>) : ConfigurationSerializable {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun deserialize(map: Map<String, Any>): ItemStackList {
            requireNotNull(map["items"]) { "Map passed as a parameter is incorrect" }

            val jsonList = map["items"]
            require(jsonList is List<*>) { "Map passed as a parameter is incorrect" }

            val list = jsonList.map {
                require(it is MutableMap<*, *>) { "Map passed as a parameter is incorrect" }

                it.forEach { (key, value) ->
                    require(key is String && value is Any) { "Map passed as a parameter is incorrect" }
                }

                ItemStack.deserialize(it as MutableMap<String, Any>)
            }

            return ItemStackList(list)
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        val map = mutableMapOf<String, Any>()

        map["items"] = itemList.map {
            it.serialize()
        }

        return map
    }
}