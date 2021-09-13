package com.mineinabyss.eternalfortune

import com.mineinabyss.eternalfortune.command.EternalFortuneCommandExecutor
import com.mineinabyss.eternalfortune.ecs.systems.UpdateNameSystem
import com.mineinabyss.eternalfortune.entity.EntitySpawner
import com.mineinabyss.eternalfortune.entity.GraveEntitySpawner
import com.mineinabyss.eternalfortune.listeners.PlayerEvents
import com.mineinabyss.eternalfortune.model.EternalFortuneRepository
import com.mineinabyss.eternalfortune.model.EternalFortuneRepositoryImpl
import com.mineinabyss.eternalfortune.model.data.ItemStackList
import com.mineinabyss.geary.ecs.api.engine.Engine
import com.mineinabyss.geary.ecs.engine.GearyEngine
import com.mineinabyss.geary.minecraft.dsl.attachToGeary
import com.mineinabyss.idofront.commands.execution.ExperimentalCommandDSL
import com.mineinabyss.idofront.plugin.getServiceOrNull
import com.mineinabyss.idofront.plugin.registerService
import com.mineinabyss.idofront.slimjar.LibraryLoaderInjector
import kotlinx.serialization.InternalSerializationApi
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.entity.Player
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

class EternalFortune : JavaPlugin() {

    @InternalSerializationApi
    @ExperimentalCommandDSL
    override fun onEnable() {
        LibraryLoaderInjector.inject(this)
        saveDefaultConfig()

        // Maybe it works as a test but to be removed for the production, I guess
        registerService<Engine>(GearyEngine(), ServicePriority.Highest)

        ConfigurationSerialization.registerClass(ItemStackList::class.java)

//        val di = module {
//            single { this } bind JavaPlugin::class
//            single { EternalFortuneRepositoryImpl() } bind EternalFortuneRepository::class
//            single { GraveEntitySpawner() } bind EntitySpawner::class
//            single { PlayerEvents() }
//            single { EternalFortuneCommandExecutor() }
//        }
//
//        startKoin {
//            modules(di)
//        }

        val repo = EternalFortuneRepositoryImpl(this)
        val spawner = GraveEntitySpawner()
        val events = PlayerEvents(repo, spawner)
        val cmd = EternalFortuneCommandExecutor(this, repo)

        attachToGeary {
            autoscanComponents()
            systems(
                // TODO: Add when working
                // UpdateNameSystem
            )
        }

        server.pluginManager.registerEvents(events, this)
    }
}