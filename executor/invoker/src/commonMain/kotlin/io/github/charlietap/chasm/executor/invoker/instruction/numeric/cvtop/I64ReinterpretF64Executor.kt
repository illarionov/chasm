package io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.execution.ExecutionContext
import io.github.charlietap.chasm.executor.runtime.ext.convertOperation
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction
import io.github.charlietap.chasm.executor.runtime.value.NumberValue.I64

internal inline fun I64ReinterpretF64Executor(
    context: ExecutionContext,
    instruction: NumericInstruction.I64ReinterpretF64,
): Result<Unit, InvocationError> {
    return context.stack.convertOperation(::I64, Double::toRawBits)
}