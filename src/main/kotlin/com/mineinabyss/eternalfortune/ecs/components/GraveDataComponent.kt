@file:UseSerializers(UUIDSerializer::class, LocalDateTimeSerializer::class)

package com.mineinabyss.eternalfortune.ecs.components

import com.mineinabyss.eternalfortune.serializers.LocalDateTimeSerializer
import com.mineinabyss.geary.ecs.api.autoscan.AutoscanComponent
import com.mineinabyss.idofront.serialization.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDateTime
import java.util.*

@Serializable
@SerialName("eternalfortune:grave-data")
@AutoscanComponent
data class GraveDataComponent (
    val graveId: UUID,
    val playerName: String,
    val expiredTime: LocalDateTime,
)
