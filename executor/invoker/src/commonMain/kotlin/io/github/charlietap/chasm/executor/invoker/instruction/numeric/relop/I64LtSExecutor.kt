package io.github.charlietap.chasm.executor.invoker.instruction.numeric.relop

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.executor.invoker.ext.lt
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.execution.ExecutionContext
import io.github.charlietap.chasm.executor.runtime.ext.relationalOperation
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction

internal inline fun I64LtSExecutor(
    context: ExecutionContext,
    instruction: NumericInstruction.I64LtS,
): Result<Unit, InvocationError> {
    return context.stack.relationalOperation(Long::lt)
}
