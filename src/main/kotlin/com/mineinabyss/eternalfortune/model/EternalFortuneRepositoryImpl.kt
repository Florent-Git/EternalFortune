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
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.util.*
import java.util.logging.Level

class EternalFortuneRepositoryImpl(private val plugin: JavaPlugin) : EternalFortuneRepository {

    init {
        plugin.apply {
            val sqllitePath = Path.of(dataFolder.path, "eternalfortune.sqlite")

            if (!Files.exists(sqllitePath)) {
                Files.createFile(sqllitePath)
                logger.log(Level.CONFIG, "Created database file")
            }

            Database.connect("jdbc:sqlite:${sqllitePath}", "org.sqlite.JDBC")

            transaction {
                addLogger(StdOutSqlLogger)

                SchemaUtils.createMissingTablesAndColumns(PlayerGraveTable)
            }
        }
    }

    override fun insertGrave(player: OfflinePlayer, deathLocation: Location, inventory: List<ItemStack>): PlayerGrave {
        lateinit var playerGrave: PlayerGrave
        transaction {
            val id = PlayerGraveTable.insert {
                it[graveId] = UUID.randomUUID()
                it[playerId] = player.uniqueId
                it[location] = deathLocation
                it[items] = ItemStackList(inventory)
                it[expiringDate] = LocalDateTime.now().plusSeconds((plugin.config["grave.time"] as Int).toLong())
            } get graveId
            playerGrave = PlayerGraveTable.toPlayerGrave(PlayerGraveTable.select { graveId.eq(id) }.first())
        }
        return playerGrave
    }

    override fun getGravesFromPlayer(player: OfflinePlayer): List<PlayerGrave> {
        lateinit var returnValue: List<PlayerGrave>

        transaction {
            returnValue = PlayerGraveTable.select { playerId.eq(player.uniqueId) }
                .map { PlayerGraveTable.toPlayerGrave(it) }
                .toList()
        }

        return returnValue
    }

    override fun getGraveCount(player: OfflinePlayer): Long {
        var returnValue = 0L

        transaction {
            returnValue = PlayerGraveTable.select { playerId eq player.uniqueId }
                .count()
        }

        return returnValue
    }

    override fun removeGrave(graveUuid: UUID) {
        transaction {
            PlayerGraveTable.deleteWhere { graveId eq graveUuid }
        }
    }

    override fun getAllGraves(): List<PlayerGrave> {
        lateinit var returnValue : List<PlayerGrave>

        transaction {
            returnValue = PlayerGraveTable.selectAll().map { PlayerGraveTable.toPlayerGrave(it) }.toList()
        }

        return returnValue
    }
}