package com.mineinabyss.eternalfortune.model.component

import com.mineinabyss.geary.ecs.api.GearyComponent
import java.util.*

data class PlayerUuidComponent(
    val uuid: UUID
) : GearyComponent()