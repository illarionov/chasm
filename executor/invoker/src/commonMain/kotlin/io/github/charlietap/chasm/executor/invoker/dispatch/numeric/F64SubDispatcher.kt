package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.binop.F64SubExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F64SubDispatcher(
    instruction: NumericInstruction.F64Sub,
) = F64SubDispatcher(
    instruction = instruction,
    executor = ::F64SubExecutor,
)

internal inline fun F64SubDispatcher(
    instruction: NumericInstruction.F64Sub,
    crossinline executor: Executor<NumericInstruction.F64Sub>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
