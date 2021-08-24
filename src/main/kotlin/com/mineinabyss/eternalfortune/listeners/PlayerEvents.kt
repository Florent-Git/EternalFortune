package com.mineinabyss.eternalfortune.listeners

import com.mineinabyss.eternalfortune.EternalFortune
import com.mineinabyss.eternalfortune.entity.EntitySpawner
import com.mineinabyss.eternalfortune.extensions.nearest
import com.mineinabyss.eternalfortune.model.EternalFortuneRepository
import com.mineinabyss.idofront.messaging.broadcastVal
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.plugin.java.JavaPlugin

class PlayerEvents(
    private val repository: EternalFortuneRepository,
    private val entitySpawner: EntitySpawner
) : Listener {

    @EventHandler
    fun PlayerDeathEvent.onPlayerDeath() {
        entitySpawner.spawn(entity)
        repository.insertGrave(entity, entity.location, drops)
        drops.clear()
    }

    @EventHandler
    fun PlayerInteractAtEntityEvent.onPlayerRightClick() {
        if (!entitySpawner.checkType(rightClicked)) return

        val graves = repository.getGravesFromPlayer(player)

        val locations = graves.map { it.location }.toTypedArray()
        val nearestGraveLocation = rightClicked.location.nearest(*locations)

        val grave = graves.first { it.location == nearestGraveLocation }

        player.inventory.addItem(*grave.items.itemList.toTypedArray())

        rightClicked.remove()
        repository.removeGrave(grave.graveId)
    }
}