package com.mineinabyss.eternalfortune.conditions

import com.mineinabyss.geary.ecs.api.conditions.GearyCondition
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.access.toBukkit
import org.bukkit.entity.Entity

class RangeCondition(private val range: IntRange = 0..15) : GearyCondition() {
    override fun GearyEntity.check(): Boolean {
        TODO("Not yet implemented")
    }
}