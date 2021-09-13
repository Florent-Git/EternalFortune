package com.mineinabyss.eternalfortune.listeners

import com.mineinabyss.eternalfortune.ecs.components.GraveDataComponent
import com.mineinabyss.eternalfortune.entity.EntitySpawner
import com.mineinabyss.eternalfortune.model.EternalFortuneRepository
import com.mineinabyss.geary.minecraft.access.geary
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.exp

class PlayerEvents(private val repository: EternalFortuneRepository, private val entitySpawner: EntitySpawner) : Listener {


    @EventHandler
    fun PlayerDeathEvent.onPlayerDeath() {
        val graveEntity = entitySpawner.spawn(entity)
        val (id, _, _, _, expiringDate) = repository.insertGrave(entity, entity.location, drops)

        geary(graveEntity).apply {
            getOrSet {
                GraveDataComponent(id, entity.name, expiringDate)
            }
        }

        drops.clear()
    }

    @EventHandler
    fun PlayerInteractAtEntityEvent.onPlayerRightClick() {
        if (!entitySpawner.checkType(rightClicked)) return

        val graves = repository.getGravesFromPlayer(player)
        val gearyGrave = geary(rightClicked)

        // TODO: Returns null on `gearyGrave.get` call
        val grave = graves.first { it.graveId == gearyGrave.get<GraveDataComponent>()?.graveId }

        player.inventory.addItem(*grave.items.itemList.toTypedArray())

        rightClicked.remove()
        repository.removeGrave(grave.graveId)
    }
}