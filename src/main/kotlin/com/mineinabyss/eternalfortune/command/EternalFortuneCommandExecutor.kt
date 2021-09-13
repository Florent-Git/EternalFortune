package com.mineinabyss.eternalfortune.command

import com.mineinabyss.eternalfortune.EternalFortune
import com.mineinabyss.eternalfortune.inventory.GraveListInventory
import com.mineinabyss.eternalfortune.model.EternalFortuneRepository
import com.mineinabyss.eternalfortune.model.data.PlayerGraveTable.expiringDate
import com.mineinabyss.idofront.commands.CommandHolder
import com.mineinabyss.idofront.commands.arguments.stringArg
import com.mineinabyss.idofront.commands.execution.ExperimentalCommandDSL
import com.mineinabyss.idofront.commands.execution.IdofrontCommandExecutor
import com.mineinabyss.idofront.plugin.registerEvents
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime
import kotlin.math.ceil

@ExperimentalCommandDSL
class EternalFortuneCommandExecutor(private val plugin: JavaPlugin, private val repository: EternalFortuneRepository) :
    IdofrontCommandExecutor() {
    override val commands: CommandHolder = commands(plugin) {
        ("eternalfortune" / "ef")(desc = "Commands for Eternal Fortune") {
            "graves"(desc = "Displays all your graves") {
                if (sender is Player) {
                    val graveInventory = GraveListInventory(repository)

                    plugin.server.pluginManager.registerEvents(graveInventory, plugin)

                    graveInventory.initInventory(sender as Player)
                    graveInventory.showInventory(sender as Player)

                    HandlerList.unregisterAll(graveInventory)
                }
            }
            "graves"(desc = "Displays all the specified player's graves") {
                val args by stringArg { name = "player" }
                val player = Bukkit.getPlayer(args)

                if (player != null) {
                    val graveInventory = GraveListInventory(repository)

                    plugin.server.pluginManager.registerEvents(graveInventory, plugin)

                    graveInventory.initInventory(player)
                    graveInventory.showInventory(player)

                    HandlerList.unregisterAll(graveInventory)
                }
            }
        }
    }
}