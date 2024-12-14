package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.relop.I64GeSExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun I64GeSDispatcher(
    instruction: NumericInstruction.I64GeS,
) = I64GeSDispatcher(
    instruction = instruction,
    executor = ::I64GeSExecutor,
)

internal inline fun I64GeSDispatcher(
    instruction: NumericInstruction.I64GeS,
    crossinline executor: Executor<NumericInstruction.I64GeS>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
