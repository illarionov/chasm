package io.github.charlietap.chasm.executor.invoker.dispatch.control

import io.github.charlietap.chasm.executor.invoker.function.HostFunctionCall
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instance.FunctionInstance
import io.github.charlietap.chasm.executor.runtime.instruction.ControlInstruction

fun HostFunctionCallDispatcher(
    instruction: ControlInstruction.HostFunctionCall,
) = HostFunctionCallDispatcher(
    instruction = instruction,
    executor = ::HostFunctionCall,
)

internal inline fun HostFunctionCallDispatcher(
    instruction: ControlInstruction.HostFunctionCall,
    crossinline executor: Executor<FunctionInstance.HostFunction>,
): DispatchableInstruction = { context ->
    executor(context, instruction.instance)
}
