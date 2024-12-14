package io.github.charlietap.chasm.executor.invoker.dispatch.memory

import io.github.charlietap.chasm.executor.invoker.instruction.memory.load.I32LoadExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.MemoryInstruction

fun I32LoadDispatcher(
    instruction: MemoryInstruction.I32Load,
) = I32LoadDispatcher(
    instruction = instruction,
    executor = ::I32LoadExecutor,
)

internal inline fun I32LoadDispatcher(
    instruction: MemoryInstruction.I32Load,
    crossinline executor: Executor<MemoryInstruction.I32Load>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
