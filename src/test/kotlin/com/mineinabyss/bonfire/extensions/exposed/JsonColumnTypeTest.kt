package com.mineinabyss.bonfire.extensions.exposed

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class JsonColumnTypeTest {
    @Test
    fun `Given a ConfigrationSerialize object, when it is passed in JsonColumnType, then it is converted into a String`() {
        val mockedSerializable = mock(ConfigurationSerializable::class.java)
        `when`(mockedSerializable.serialize()).then {
                mapOf<String, Any>(
                    "Name" to "Florent",
                    "Age" to 21,
                    "Dead" to false
                )
        }

        val mock = mock(JsonColumnType::class.java)

        assertThat(mock.nonNullValueToString(mockedSerializable), equalTo("""
            {"Name":"Florent","Age":21,"Dead":false}
        """.trimIndent()))
    }
}