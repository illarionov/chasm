package io.github.charlietap.chasm.plugin.suitegen.script

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ModuleType {
    @SerialName("binary")
    Binary,
    @SerialName("text")
    Text,
}
