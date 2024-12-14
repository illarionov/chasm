package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.binop.F32MulExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F32MulDispatcher(
    instruction: NumericInstruction.F32Mul,
) = F32MulDispatcher(
    instruction = instruction,
    executor = ::F32MulExecutor,
)

internal inline fun F32MulDispatcher(
    instruction: NumericInstruction.F32Mul,
    crossinline executor: Executor<NumericInstruction.F32Mul>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
