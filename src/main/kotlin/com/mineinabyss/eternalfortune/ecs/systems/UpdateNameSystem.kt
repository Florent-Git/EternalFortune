package com.mineinabyss.eternalfortune.ecs.systems

import com.mineinabyss.eternalfortune.ecs.components.GraveDataComponent
import com.mineinabyss.geary.ecs.api.systems.TickingSystem
import com.mineinabyss.geary.ecs.engine.iteration.QueryResult
import com.mineinabyss.geary.minecraft.access.toBukkit
import net.kyori.adventure.text.Component
import org.bukkit.entity.Entity
import java.time.Duration
import java.time.LocalDateTime

object UpdateNameSystem : TickingSystem(20) {
    private val QueryResult.graveData by get<GraveDataComponent>()

    override fun QueryResult.tick() {
        val bukkit = entity.toBukkit<Entity>()
        requireNotNull(bukkit) { "Entity cannot be null" }

        bukkit.customName(graveData.toTextComponent())
    }

    private fun GraveDataComponent.toTextComponent(): Component = Component.text()
        .content("$playerName's grave")
        .content("Remaining time: ${(expiredTime - LocalDateTime.now()).toRemainingTimeString()}")
        .build()

    private operator fun LocalDateTime.minus(date: LocalDateTime): Duration {
        return Duration.between(this, date)
    }

    private fun Duration.toRemainingTimeString(): String {
        return "${toDays()}d ${toHours()}h ${toMinutes()}m ${seconds}s"
    }
}