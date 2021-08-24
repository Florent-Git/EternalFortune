package com.mineinabyss.eternalfortune.model.data

import com.mineinabyss.bonfire.extensions.exposed.itemStackList
import com.mineinabyss.bonfire.extensions.exposed.location
import org.bukkit.Location
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*
import kotlin.math.exp

object PlayerGraveTable : IdTable<UUID>() {
    val graveId = uuid("grave_id").uniqueIndex()

    val playerId = uuid("player_id")
    val location = location("player_location")
    val items = itemStackList("items")

    val expiringDate = datetime("expiringDate")

    override val id: Column<EntityID<UUID>>
        get() = graveId.entityId()

    fun insert(grave: PlayerGrave) {
        transaction {
            insert {
                it[graveId] = grave.graveId
                it[playerId] = grave.playerId
                it[location] = grave.location
                it[items] = grave.items
                it[expiringDate] = grave.expiringDate
            }
        }
    }

    fun update(grave: PlayerGrave) {
        val playerGrave = toPlayerGrave(select { graveId eq grave.graveId }.single())
        val (graveId, playerId, location, items, expiringDate) = grave
        val updated = playerGrave.copy(graveId, playerId, location, items, expiringDate)

        transaction {
            update(updated)
        }
    }

    fun toPlayerGrave(row: ResultRow): PlayerGrave = PlayerGrave(
        row[graveId],
        row[playerId],
        row[location],
        row[items],
        row[expiringDate]
    )
}

data class PlayerGrave(
    val graveId: UUID,
    val playerId: UUID,
    val location: Location,
    val items: ItemStackList,
    val expiringDate: LocalDateTime
)