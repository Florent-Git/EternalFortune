package com.mineinabyss.eternalfortune.extensions

import be.seeseemelk.mockbukkit.MockPlugin
import be.seeseemelk.mockbukkit.ServerMock
import org.bukkit.Location
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocationExtensionTest {
    private lateinit var server: ServerMock

    @BeforeEach
    fun setup() {
        server = ServerMock()
        server.addSimpleWorld("da worudo")
    }

    @Test
    fun `Given three locations, give the nearest`() {
        val loc1 = Location(server.worlds.first(), 10.0, 10.0, 10.0)
        val loc2 = Location(server.worlds.first(), 0.0, 0.0, 0.0)
        val loc3 = Location(server.worlds.first(), -10.0, -10.0, -10.0)

        assertThat(loc1.nearest(loc2, loc3), equalTo(loc2))
    }
}