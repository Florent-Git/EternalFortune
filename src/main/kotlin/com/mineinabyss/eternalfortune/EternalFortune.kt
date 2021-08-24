package com.mineinabyss.eternalfortune

import com.mineinabyss.eternalfortune.entity.EntitySpawner
import com.mineinabyss.eternalfortune.entity.GraveEntitySpawner
import com.mineinabyss.eternalfortune.listeners.PlayerEvents
import com.mineinabyss.eternalfortune.model.EternalFortuneRepositoryImpl
import com.mineinabyss.eternalfortune.model.data.ItemStackList
import com.mineinabyss.idofront.slimjar.LibraryLoaderInjector
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

// TODO: Handle DI more efficiently
class EternalFortune : JavaPlugin() {
    private lateinit var repository: EternalFortuneRepositoryImpl
    private lateinit var entitySpawner : EntitySpawner

    override fun onEnable() {
        LibraryLoaderInjector.inject(this)
        saveDefaultConfig()

        repository = EternalFortuneRepositoryImpl(this)
        entitySpawner = GraveEntitySpawner()
        ConfigurationSerialization.registerClass(ItemStackList::class.java)

        val playerEvents = PlayerEvents(repository, entitySpawner)
        server.pluginManager.registerEvents(playerEvents, this)

        logger.info(dataFolder.path)
    }
}