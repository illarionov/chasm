package io.github.charlietap.chasm.executor.invoker.dispatch.numeric

import io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop.F32ReinterpretI32Executor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

fun F32ReinterpretI32Dispatcher(
    instruction: NumericInstruction.F32ReinterpretI32,
) = F32ReinterpretI32Dispatcher(
    instruction = instruction,
    executor = ::F32ReinterpretI32Executor,
)

internal inline fun F32ReinterpretI32Dispatcher(
    instruction: NumericInstruction.F32ReinterpretI32,
    crossinline executor: Executor<NumericInstruction.F32ReinterpretI32>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}