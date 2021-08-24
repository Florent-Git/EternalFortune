package com.mineinabyss.eternalfortune.listeners

import com.mineinabyss.eternalfortune.entity.EntitySpawner
import com.mineinabyss.eternalfortune.model.EternalFortuneRepository
import com.mineinabyss.eternalfortune.model.EternalFortuneRepositoryImpl
import com.mineinabyss.eternalfortune.model.component.PlayerUuidComponent
import com.mineinabyss.geary.minecraft.access.geary
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEntityEvent

class PlayerEvents(
    private val repository: EternalFortuneRepository,
    private val entitySpawner: EntitySpawner
) : Listener {

    @EventHandler
    fun PlayerDeathEvent.onPlayerDeath() {
        entitySpawner.spawn(entity)
        repository.insertGrave(entity, entity.location, drops)
    }

    @EventHandler
    fun PlayerInteractEntityEvent.onPlayerRightClick() {

    }
}