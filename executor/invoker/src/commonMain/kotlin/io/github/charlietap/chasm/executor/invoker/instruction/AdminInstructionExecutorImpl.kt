package io.github.charlietap.chasm.executor.invoker.instruction

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.executor.invoker.instruction.admin.FrameInstructionExecutor
import io.github.charlietap.chasm.executor.invoker.instruction.admin.FrameInstructionExecutorImpl
import io.github.charlietap.chasm.executor.invoker.instruction.admin.LabelInstructionExecutor
import io.github.charlietap.chasm.executor.invoker.instruction.admin.LabelInstructionExecutorImpl
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.instruction.AdminInstruction
import io.github.charlietap.chasm.executor.runtime.instruction.ExecutionInstruction
import io.github.charlietap.chasm.executor.runtime.store.Store

internal fun AdminInstructionExecutorImpl(
    instruction: ExecutionInstruction,
    store: Store,
    stack: Stack,
): Result<Unit, InvocationError> =
    AdminInstructionExecutorImpl(
        instruction = instruction,
        store = store,
        stack = stack,
        frameInstructionExecutor = ::FrameInstructionExecutorImpl,
        labelInstructionExecutor = ::LabelInstructionExecutorImpl,
    )

@Suppress("UNUSED_PARAMETER")
internal fun AdminInstructionExecutorImpl(
    instruction: ExecutionInstruction,
    store: Store,
    stack: Stack,
    frameInstructionExecutor: FrameInstructionExecutor,
    labelInstructionExecutor: LabelInstructionExecutor,
): Result<Unit, InvocationError> = binding {
    when (instruction) {
        is AdminInstruction.Frame -> frameInstructionExecutor(instruction.frame, stack).bind()
        is AdminInstruction.Label -> labelInstructionExecutor(instruction.label, stack).bind()

        else -> Err(InvocationError.UnimplementedInstruction(instruction)).bind<Unit>()
    }
}
