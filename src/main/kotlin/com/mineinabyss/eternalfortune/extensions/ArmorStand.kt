package com.mineinabyss.eternalfortune.extensions

import org.bukkit.entity.ArmorStand
import org.bukkit.inventory.EquipmentSlot

fun ArmorStand.disableAllSlots() {
    EquipmentSlot.values().forEach { this.addDisabledSlots(it) }
}