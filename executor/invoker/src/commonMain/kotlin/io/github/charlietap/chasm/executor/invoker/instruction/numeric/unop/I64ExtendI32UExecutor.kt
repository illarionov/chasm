@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.numeric.unop

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.ast.instruction.NumericInstruction
import io.github.charlietap.chasm.executor.invoker.context.ExecutionContext
import io.github.charlietap.chasm.executor.invoker.ext.extendI64u
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.convertOperation
import io.github.charlietap.chasm.executor.runtime.value.NumberValue.I64

internal inline fun I64ExtendI32UExecutor(
    context: ExecutionContext,
    instruction: NumericInstruction.I64ExtendI32U,
): Result<Unit, InvocationError> {
    return context.stack.convertOperation(::I64, Int::extendI64u)
}
