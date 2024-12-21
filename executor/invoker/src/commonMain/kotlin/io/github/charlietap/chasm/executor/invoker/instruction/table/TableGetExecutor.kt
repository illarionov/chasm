package io.github.charlietap.chasm.executor.invoker.instruction.table

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.execution.ExecutionContext
import io.github.charlietap.chasm.executor.runtime.ext.element
import io.github.charlietap.chasm.executor.runtime.ext.popI32
import io.github.charlietap.chasm.executor.runtime.instruction.TableInstruction

internal inline fun TableGetExecutor(
    context: ExecutionContext,
    instruction: TableInstruction.TableGet,
): Result<Unit, InvocationError> = binding {

    val (stack) = context
    val tableInstance = instruction.table

    val elementIndex = stack.popI32().bind()
    if (elementIndex >= tableInstance.elements.size) {
        Err(InvocationError.Trap.TrapEncountered).bind<Unit>()
    }

    val referenceValue = tableInstance.element(elementIndex).bind()

    stack.push(Stack.Entry.Value(referenceValue))
}
