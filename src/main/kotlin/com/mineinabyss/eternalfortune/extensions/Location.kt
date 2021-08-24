package com.mineinabyss.eternalfortune.extensions

import org.bukkit.Location

fun Location.nearest(vararg location: Location): Location = location.reduce { acc, loc ->
    if (acc.distance(this@nearest) > loc.distance(this@nearest))
        return loc
    return acc
}