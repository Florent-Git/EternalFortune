package com.mineinabyss.eternalfortune.inventory

import com.mineinabyss.eternalfortune.model.EternalFortuneRepository
import com.mineinabyss.eternalfortune.model.data.PlayerGrave
import com.mineinabyss.idofront.items.editItemMeta
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Nameable
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.time.Duration
import java.time.LocalDateTime

class GraveListInventory(private val repository: EternalFortuneRepository) : Listener {
    private lateinit var inv: Inventory

    fun initInventory(player: Player) {
        val graves = repository.getGravesFromPlayer(player)

        inv = Bukkit.createInventory(null, ((graves.size - 1) / 9 * 9) + 9, getInventoryTitle(player))

        graves.forEach {
            inv.addItem(createGrave(it))
        }
    }

    fun showInventory(player: HumanEntity) {
        player.openInventory(inv)
    }

    @EventHandler
    fun InventoryOpenEvent.onOpenEvent() {
        if (player !is Player) return
        initInventory(player as Player)
    }

    @EventHandler
    fun InventoryClickEvent.onClickEvent() {
        isCancelled = inventory == inv
    }

    @EventHandler
    fun InventoryDragEvent.onDragEvent() {
        isCancelled = inventory == inv
    }

    protected fun createGrave(grave: PlayerGrave): ItemStack {
        val item = ItemStack(Material.MOSSY_COBBLESTONE)

        item.editItemMeta {
            displayName(getGravePosition(grave.location))
            lore(lore()?.also {
                it.add(getGraveTimeRemaining(grave.expiringDate))
            } ?: listOf(getGraveTimeRemaining(grave.expiringDate)))
        }

        return item
    }

    protected fun getInventoryTitle(player: Player): TextComponent {
        val playerName = player.customName ?: player.name
        return Component.text()
            .content("${playerName}'s graves")
            .build()
    }

    protected fun getGravePosition(location: Location): TextComponent = Component.text()
        .content("X: ${location.blockX}, Y: ${location.blockY}, Z: ${location.blockZ}")
        .build()

    protected fun getGraveTimeRemaining(localDateTime: LocalDateTime): TextComponent {
        val remaining = Duration.between(localDateTime, LocalDateTime.now())

        return if (remaining.isNegative || remaining.isZero) {
            Component.text()
                .content("EXPIRED")
                .color(TextColor.color(1f, 0f, 0f))
                .build()
        } else {
            Component.text()
                .content("Time remaining: $remaining")
                .build()
        }
    }
}