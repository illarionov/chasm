package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.relop.I32LeUExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun I32LeUDispatcher(
    instruction: NumericInstruction.I32LeU,
) = I32LeUDispatcher(
    instruction = instruction,
    executor = ::I32LeUExecutor,
)

internal inline fun I32LeUDispatcher(
    instruction: NumericInstruction.I32LeU,
    crossinline executor: Executor<NumericInstruction.I32LeU>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
