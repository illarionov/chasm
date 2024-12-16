package io.github.charlietap.chasm.executor.invoker.dispatch.table

import io.github.charlietap.chasm.executor.invoker.instruction.table.TableCopyExecutor
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.execution.Executor
import io.github.charlietap.chasm.executor.runtime.instruction.TableInstruction

fun TableCopyDispatcher(
    instruction: TableInstruction.TableCopy,
) = TableCopyDispatcher(
    instruction = instruction,
    executor = ::TableCopyExecutor,
)

internal inline fun TableCopyDispatcher(
    instruction: TableInstruction.TableCopy,
    crossinline executor: Executor<TableInstruction.TableCopy>,
): DispatchableInstruction = { context ->
    executor(context, instruction)
}