package io.github.charlietap.chasm.executor.invoker.dispatch.control

import io.github.charlietap.chasm.executor.invoker.instruction.control.ReturnExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.ControlInstruction

fun ReturnDispatcher(
    instruction: ControlInstruction.Return,
) = ReturnDispatcher(
    instruction = instruction,
    executor = ::ReturnExecutor,
)

internal inline fun ReturnDispatcher(
    instruction: ControlInstruction.Return,
    crossinline executor: Executor<ControlInstruction.Return>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
