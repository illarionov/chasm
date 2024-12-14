package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop.F64ConvertI64UExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F64ConvertI64UDispatcher(
    instruction: NumericInstruction.F64ConvertI64U,
) = F64ConvertI64UDispatcher(
    instruction = instruction,
    executor = ::F64ConvertI64UExecutor,
)

internal inline fun F64ConvertI64UDispatcher(
    instruction: NumericInstruction.F64ConvertI64U,
    crossinline executor: Executor<NumericInstruction.F64ConvertI64U>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
