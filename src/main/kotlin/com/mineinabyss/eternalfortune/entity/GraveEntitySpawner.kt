package com.mineinabyss.eternalfortune.entity

import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.EulerAngle

class GraveEntitySpawner : EntitySpawner {
    override fun spawn(player: Player): Entity {
        val location = player.location
        location.y -= 1.25

        val armorStand: ArmorStand = player.world.spawnEntity(location, EntityType.ARMOR_STAND) as ArmorStand
        val equipment = armorStand.equipment!!

        equipment.helmet = ItemStack(Material.MOSSY_COBBLESTONE_SLAB)

        armorStand.headPose = EulerAngle(90.0, 0.0, 0.0)
        armorStand.isInvisible = true
        armorStand.setGravity(false)
        return armorStand
    }
}