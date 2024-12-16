package io.github.charlietap.chasm.executor.invoker.dispatch.control

import io.github.charlietap.chasm.executor.invoker.instruction.control.ReturnCallRefExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.ControlInstruction

fun ReturnCallRefDispatcher(
    instruction: ControlInstruction.ReturnCallRef,
) = ReturnCallRefDispatcher(
    instruction = instruction,
    executor = ::ReturnCallRefExecutor,
)

internal inline fun ReturnCallRefDispatcher(
    instruction: ControlInstruction.ReturnCallRef,
    crossinline executor: Executor<ControlInstruction.ReturnCallRef>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}