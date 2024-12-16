package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop.F32ConvertI32SExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F32ConvertI32SDispatcher(
    instruction: NumericInstruction.F32ConvertI32S,
) = F32ConvertI32SDispatcher(
    instruction = instruction,
    executor = ::F32ConvertI32SExecutor,
)

internal inline fun F32ConvertI32SDispatcher(
    instruction: NumericInstruction.F32ConvertI32S,
    crossinline executor: Executor<NumericInstruction.F32ConvertI32S>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}