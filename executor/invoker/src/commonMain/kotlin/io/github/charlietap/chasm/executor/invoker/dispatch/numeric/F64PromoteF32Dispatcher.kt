package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop.F64PromoteF32Executor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F64PromoteF32Dispatcher(
    instruction: NumericInstruction.F64PromoteF32,
) = F64PromoteF32Dispatcher(
    instruction = instruction,
    executor = ::F64PromoteF32Executor,
)

internal inline fun F64PromoteF32Dispatcher(
    instruction: NumericInstruction.F64PromoteF32,
    crossinline executor: Executor<NumericInstruction.F64PromoteF32>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
