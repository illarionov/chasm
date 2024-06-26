package io.github.charlietap.chasm.executor.runtime

import io.github.charlietap.chasm.executor.runtime.instruction.ExecutionInstruction

data class Thread(
    val frame: Stack.Entry.ActivationFrame,
    val instructions: List<ExecutionInstruction>,
)
