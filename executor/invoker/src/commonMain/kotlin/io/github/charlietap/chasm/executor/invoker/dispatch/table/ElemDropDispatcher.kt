package io.github.charlietap.chasm.executor.invoker.dispatch.table

import io.github.charlietap.chasm.executor.invoker.instruction.table.ElementDropExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.TableInstruction

fun ElemDropDispatcher(
    instruction: TableInstruction.ElemDrop,
) = ElemDropDispatcher(
    instruction = instruction,
    executor = ::ElementDropExecutor,
)

internal inline fun ElemDropDispatcher(
    instruction: TableInstruction.ElemDrop,
    crossinline executor: Executor<TableInstruction.ElemDrop>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}
