package com.mineinabyss.eternalfortune.model

import com.mineinabyss.eternalfortune.model.data.PlayerGrave
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import java.util.*

interface EternalFortuneRepository {
    fun insertGrave(player: OfflinePlayer, deathLocation: Location, inventory: List<ItemStack>)
    fun getGravesFromPlayer(player: OfflinePlayer): List<PlayerGrave>
    fun getGraveCount(player: OfflinePlayer): Long
    fun removeGrave(graveUuid: UUID)
}