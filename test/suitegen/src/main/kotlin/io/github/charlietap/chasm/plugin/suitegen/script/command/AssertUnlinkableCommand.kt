package io.github.charlietap.chasm.plugin.suitegen.script.command

import io.github.charlietap.chasm.plugin.suitegen.script.ModuleType
import io.github.charlietap.chasm.plugin.suitegen.script.action.Action
import io.github.charlietap.chasm.plugin.suitegen.script.value.Value
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("assert_unlinkable")
data class AssertUnlinkableCommand(
    override val filename: String,
    override val line: Int,
    val action: Action,
    val expected: List<Value>,
    val text: String,
    val moduleType: ModuleType,
): Command
