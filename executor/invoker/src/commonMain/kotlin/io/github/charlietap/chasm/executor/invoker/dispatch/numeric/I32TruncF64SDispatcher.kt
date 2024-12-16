package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop.I32TruncF64SExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun I32TruncF64SDispatcher(
    instruction: NumericInstruction.I32TruncF64S,
) = I32TruncF64SDispatcher(
    instruction = instruction,
    executor = ::I32TruncF64SExecutor,
)

internal inline fun I32TruncF64SDispatcher(
    instruction: NumericInstruction.I32TruncF64S,
    crossinline executor: Executor<NumericInstruction.I32TruncF64S>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}