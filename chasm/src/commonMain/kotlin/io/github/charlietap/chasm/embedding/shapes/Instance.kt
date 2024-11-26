package io.github.charlietap.chasm.embedding.shapes

import io.github.charlietap.chasm.embedding.exports
import io.github.charlietap.chasm.executor.runtime.instance.ModuleInstance

class Instance internal constructor(
    internal val instance: ModuleInstance,
) {
    val exports by lazy {
        exports(this)
    }
}
