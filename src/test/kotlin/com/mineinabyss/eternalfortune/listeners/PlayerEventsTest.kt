package com.mineinabyss.eternalfortune.listeners

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.MockPlugin
import be.seeseemelk.mockbukkit.ServerMock
import com.mineinabyss.eternalfortune.entity.EntitySpawner
import com.mineinabyss.eternalfortune.model.EternalFortuneRepository
import org.junit.Ignore
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class PlayerEventsTest {
    private lateinit var serverMock: ServerMock
    private lateinit var pluginMock: MockPlugin

    private lateinit var playerEvents: PlayerEvents

    @BeforeEach
    fun setup() {
        serverMock = MockBukkit.mock()
        pluginMock = MockBukkit.createMockPlugin()
    }

    @Test
    @Ignore("Ok it seems like Unit testing is really hard to do on a minecraft plugin")
    fun `Given a player, When it dies, Then should execute repository methods`() {
        val repositoryMock = mock<EternalFortuneRepository> { }
        val entitySpawnerMock = mock<EntitySpawner> { }

        playerEvents = PlayerEvents(repositoryMock, entitySpawnerMock)

        serverMock.pluginManager.registerEvents(playerEvents, pluginMock)

        serverMock.setPlayers(1)
        serverMock.getPlayer(1).health = 0.0

        verify(repositoryMock).insertGrave(any(), any(), any())
    }
}