package io.github.charlietap.chasm.executor.invoker.dispatch.memory

import io.github.charlietap.chasm.executor.invoker.instruction.memory.store.F64StoreExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.MemoryInstruction

fun F64StoreDispatcher(
    instruction: MemoryInstruction.F64Store,
) = F64StoreDispatcher(
    instruction = instruction,
    executor = ::F64StoreExecutor,
)

internal inline fun F64StoreDispatcher(
    instruction: MemoryInstruction.F64Store,
    crossinline executor: Executor<MemoryInstruction.F64Store>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
