package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop.I32TruncF32UExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun I32TruncF32UDispatcher(
    instruction: NumericInstruction.I32TruncF32U,
) = I32TruncF32UDispatcher(
    instruction = instruction,
    executor = ::I32TruncF32UExecutor,
)

internal inline fun I32TruncF32UDispatcher(
    instruction: NumericInstruction.I32TruncF32U,
    crossinline executor: Executor<NumericInstruction.I32TruncF32U>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
