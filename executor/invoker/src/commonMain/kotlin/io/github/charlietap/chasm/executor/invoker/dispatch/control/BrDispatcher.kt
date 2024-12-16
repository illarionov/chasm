package io.github.charlietap.chasm.executor.invoker.dispatch.control

import io.github.charlietap.chasm.executor.invoker.instruction.control.BreakExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.ControlInstruction

fun BrDispatcher(
    instruction: ControlInstruction.Br,
) = BrDispatcher(
    instruction = instruction,
    executor = ::BreakExecutor,
)

internal inline fun BrDispatcher(
    instruction: ControlInstruction.Br,
    crossinline executor: Executor<ControlInstruction.Br>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}