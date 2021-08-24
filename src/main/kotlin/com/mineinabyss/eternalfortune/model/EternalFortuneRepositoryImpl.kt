package com.mineinabyss.eternalfortune.model

import com.mineinabyss.eternalfortune.EternalFortune
import com.mineinabyss.eternalfortune.model.data.ItemStackList
import com.mineinabyss.eternalfortune.model.data.PlayerGraveTable
import com.mineinabyss.eternalfortune.model.data.PlayerGraveTable.expiringDate
import com.mineinabyss.eternalfortune.model.data.PlayerGraveTable.graveId
import com.mineinabyss.eternalfortune.model.data.PlayerGraveTable.items
import com.mineinabyss.eternalfortune.model.data.PlayerGraveTable.location
import com.mineinabyss.eternalfortune.model.data.PlayerGraveTable.playerId
import com.mineinabyss.eternalfortune.model.data.PlayerGrave
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.util.logging.Level

class EternalFortuneRepositoryImpl(private val plugin: EternalFortune): EternalFortuneRepository {
    init {
        plugin.apply {
            val sqllitePath = Path.of(dataFolder.path, "eternalfortune.sqlite")

            if (!Files.exists(sqllitePath)) {
                Files.createFile(sqllitePath)
                logger.log(Level.CONFIG,"Created database file")
            }

            Database.connect("jdbc:sqlite:${sqllitePath}", "org.sqlite.JDBC")

            transaction {
                addLogger(StdOutSqlLogger)

                SchemaUtils.createMissingTablesAndColumns(PlayerGraveTable)
            }
        }
    }

    override fun insertGrave(player: OfflinePlayer, deathLocation: Location, inventory: List<ItemStack>) {
        transaction {
            PlayerGraveTable.insert {
                it[playerId] = player.uniqueId
                it[location] = deathLocation
                it[items] = ItemStackList(inventory)
                it[expiringDate] = LocalDateTime.now().plusSeconds((plugin.config["grave.time"] as Int).toLong())
            }
        }
    }

    override fun getGravesFromPlayer(player: OfflinePlayer): List<PlayerGrave> {
        return PlayerGraveTable.select { playerId.eq(player.uniqueId) }
            .map { PlayerGraveTable.toPlayerGrave(it) }
            .toList()
    }
}