package com.mineinabyss.eternalfortune.entity

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface EntitySpawner {
    fun spawn(player: Player): Entity
    fun checkType(entity: Entity): Boolean
}