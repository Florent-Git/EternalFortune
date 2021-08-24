package com.mineinabyss.eternalfortune

import com.mineinabyss.idofront.commands.CommandHolder
import com.mineinabyss.idofront.commands.execution.ExperimentalCommandDSL
import com.mineinabyss.idofront.commands.execution.IdofrontCommandExecutor

@ExperimentalCommandDSL
class EternalFortuneCommandExecutor(plugin: EternalFortune) : IdofrontCommandExecutor() {
    override val commands: CommandHolder = commands(plugin) {
        ("eternalfortune" / "ef")(desc = "Commands for Eternal Fortune") {
            "graves"(desc = "Displays the time remaining")
        }
    }
}