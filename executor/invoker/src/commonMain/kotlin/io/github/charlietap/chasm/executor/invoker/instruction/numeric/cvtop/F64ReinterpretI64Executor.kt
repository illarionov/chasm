@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.ast.instruction.NumericInstruction
import io.github.charlietap.chasm.executor.invoker.context.ExecutionContext
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.convertOperation
import io.github.charlietap.chasm.executor.runtime.value.NumberValue.F64

internal inline fun F64ReinterpretI64Executor(
    context: ExecutionContext,
    instruction: NumericInstruction.F64ReinterpretI64,
): Result<Unit, InvocationError> {
    return context.stack.convertOperation(::F64, Double::fromBits)
}
