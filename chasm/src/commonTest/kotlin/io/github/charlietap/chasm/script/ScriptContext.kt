package io.github.charlietap.chasm.script

import io.github.charlietap.chasm.executor.runtime.instance.ModuleInstance
import io.github.charlietap.chasm.executor.runtime.store.Store

data class ScriptContext(
    val binaryDirectory: String,
    val store: Store,
    val instances: MutableMap<String?, ModuleInstance>,
)
