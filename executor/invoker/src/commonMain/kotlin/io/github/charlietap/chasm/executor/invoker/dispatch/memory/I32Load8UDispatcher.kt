package io.github.charlietap.chasm.executor.invoker.dispatch.memory

import io.github.charlietap.chasm.executor.invoker.instruction.memory.load.I32Load8UExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.MemoryInstruction

fun I32Load8UDispatcher(
    instruction: MemoryInstruction.I32Load8U,
) = I32Load8UDispatcher(
    instruction = instruction,
    executor = ::I32Load8UExecutor,
)

internal inline fun I32Load8UDispatcher(
    instruction: MemoryInstruction.I32Load8U,
    crossinline executor: Executor<MemoryInstruction.I32Load8U>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}