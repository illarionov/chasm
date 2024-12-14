package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.relop.F64NeExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F64NeDispatcher(
    instruction: NumericInstruction.F64Ne,
) = F64NeDispatcher(
    instruction = instruction,
    executor = ::F64NeExecutor,
)

internal inline fun F64NeDispatcher(
    instruction: NumericInstruction.F64Ne,
    crossinline executor: Executor<NumericInstruction.F64Ne>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
