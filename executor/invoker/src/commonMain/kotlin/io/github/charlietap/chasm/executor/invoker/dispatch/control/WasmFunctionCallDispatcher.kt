package io.github.charlietap.chasm.executor.invoker.dispatch.control

import io.github.charlietap.chasm.executor.invoker.function.WasmFunctionCall
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instance.FunctionInstance.WasmFunction
import io.github.charlietap.chasm.executor.runtime.instruction.ControlInstruction

fun WasmFunctionCallDispatcher(
    instruction: ControlInstruction.WasmFunctionCall,
) = WasmFunctionCallDispatcher(
    instruction = instruction,
    executor = ::WasmFunctionCall,
)

internal inline fun WasmFunctionCallDispatcher(
    instruction: ControlInstruction.WasmFunctionCall,
    crossinline executor: Executor<WasmFunction>,
): DispatchableInstruction = { context ->
    executor(context, instruction.instance)
}
