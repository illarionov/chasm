package io.github.charlietap.chasm.executor.invoker.dispatch.memory

import io.github.charlietap.chasm.executor.invoker.instruction.memory.load.F64LoadExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.MemoryInstruction

fun F64LoadDispatcher(
    instruction: MemoryInstruction.F64Load,
) = F64LoadDispatcher(
    instruction = instruction,
    executor = ::F64LoadExecutor,
)

internal inline fun F64LoadDispatcher(
    instruction: MemoryInstruction.F64Load,
    crossinline executor: Executor<MemoryInstruction.F64Load>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}