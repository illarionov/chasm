package io.github.charlietap.chasm.plugin.suitegen.script.command

import kotlinx.serialization.Serializable

@Serializable
sealed interface Command {
    abstract val filename: String
    abstract val line: Int
}
