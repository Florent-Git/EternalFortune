package com.mineinabyss.eternalfortune.entity

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface EntitySpawner {
    fun spawn(entity: Entity): Entity
    fun checkType(entity: Entity): Boolean
}