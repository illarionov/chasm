package io.github.charlietap.chasm.executor.invoker.instruction.numeric.cvtop

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.execution.ExecutionContext
import io.github.charlietap.chasm.executor.runtime.ext.convertOperation
import io.github.charlietap.chasm.executor.runtime.instruction.NumericInstruction
import io.github.charlietap.chasm.executor.runtime.value.NumberValue.F32

internal inline fun F32ReinterpretI32Executor(
    context: ExecutionContext,
    instruction: NumericInstruction.F32ReinterpretI32,
): Result<Unit, InvocationError> {
    return context.stack.convertOperation(::F32, Float::fromBits)
}
