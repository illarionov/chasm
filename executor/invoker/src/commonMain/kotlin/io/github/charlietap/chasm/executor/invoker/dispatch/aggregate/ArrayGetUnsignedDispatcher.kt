package io.github.charlietap.chasm.executor.invoker.dispatch.aggregate

import io.github.charlietap.chasm.executor.invoker.instruction.aggregate.ArrayGetUnsignedExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.AggregateInstruction

fun ArrayGetUnsignedDispatcher(
    instruction: AggregateInstruction.ArrayGetUnsigned,
) = ArrayGetUnsignedDispatcher(
    instruction = instruction,
    executor = ::ArrayGetUnsignedExecutor,
)

internal inline fun ArrayGetUnsignedDispatcher(
    instruction: AggregateInstruction.ArrayGetUnsigned,
    crossinline executor: Executor<AggregateInstruction.ArrayGetUnsigned>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
