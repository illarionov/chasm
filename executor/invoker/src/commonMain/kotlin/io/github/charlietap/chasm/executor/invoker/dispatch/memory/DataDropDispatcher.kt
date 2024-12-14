package io.github.charlietap.chasm.executor.invoker.dispatch.memory

import io.github.charlietap.chasm.executor.invoker.instruction.memory.DataDropExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.MemoryInstruction

fun DataDropDispatcher(
    instruction: MemoryInstruction.DataDrop,
) = DataDropDispatcher(
    instruction = instruction,
    executor = ::DataDropExecutor,
)

internal inline fun DataDropDispatcher(
    instruction: MemoryInstruction.DataDrop,
    crossinline executor: Executor<MemoryInstruction.DataDrop>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
