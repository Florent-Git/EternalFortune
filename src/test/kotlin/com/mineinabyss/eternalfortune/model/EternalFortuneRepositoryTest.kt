package com.mineinabyss.eternalfortune.model

import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.BeforeEach

class EternalFortuneRepositoryTest {
    @BeforeEach
    fun setup() {
        Database.connect("jdbc:h2")
    }
}