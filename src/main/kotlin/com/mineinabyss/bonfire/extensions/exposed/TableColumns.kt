package com.mineinabyss.bonfire.extensions.exposed

import com.mineinabyss.eternalfortune.model.data.ItemStackList
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.Table

// TODO: This file is from the bonfire repo. These extensions might be useful in the "common" repo, tdb

/**
 * A profile property set column to [Location]s using a
 * varchar column.
 *
 * @param name the name of the column
 */
fun Table.location(name: String) = registerColumn<Location>(name, LocationColumnType())

fun Table.itemStackList(name: String) = registerColumn<ItemStackList>(name, ItemStackListColumnType())