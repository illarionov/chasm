package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop.F32ConvertI32UExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F32ConvertI32UDispatcher(
    instruction: NumericInstruction.F32ConvertI32U,
) = F32ConvertI32UDispatcher(
    instruction = instruction,
    executor = ::F32ConvertI32UExecutor,
)

internal inline fun F32ConvertI32UDispatcher(
    instruction: NumericInstruction.F32ConvertI32U,
    crossinline executor: Executor<NumericInstruction.F32ConvertI32U>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
