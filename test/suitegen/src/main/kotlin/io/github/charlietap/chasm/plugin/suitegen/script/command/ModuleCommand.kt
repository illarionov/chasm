package io.github.charlietap.chasm.plugin.suitegen.script.command

import io.github.charlietap.chasm.plugin.suitegen.script.command.Command
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("module")
data class ModuleCommand(
    override val filename: String,
    override val line: Int,
): Command
