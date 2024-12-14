package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.relop.F64LeExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F64LeDispatcher(
    instruction: NumericInstruction.F64Le,
) = F64LeDispatcher(
    instruction = instruction,
    executor = ::F64LeExecutor,
)

internal inline fun F64LeDispatcher(
    instruction: NumericInstruction.F64Le,
    crossinline executor: Executor<NumericInstruction.F64Le>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
