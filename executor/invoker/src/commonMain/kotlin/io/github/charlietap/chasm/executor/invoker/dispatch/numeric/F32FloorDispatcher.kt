package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.unop.F32FloorExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F32FloorDispatcher(
    instruction: NumericInstruction.F32Floor,
) = F32FloorDispatcher(
    instruction = instruction,
    executor = ::F32FloorExecutor,
)

internal inline fun F32FloorDispatcher(
    instruction: NumericInstruction.F32Floor,
    crossinline executor: Executor<NumericInstruction.F32Floor>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
