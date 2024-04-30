package io.github.charlietap.chasm.plugin.suitegen.script.action

import io.github.charlietap.chasm.plugin.suitegen.script.value.Value
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("invoke")
data class InvokeAction(
    val field: String,
    val args: List<Value>,
): Action
