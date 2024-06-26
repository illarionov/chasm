package io.github.charlietap.chasm.executor.invoker.instruction.reference

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.ReferenceInstruction
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.instruction.ModuleInstruction
import io.github.charlietap.chasm.executor.runtime.store.Store

internal fun ReferenceInstructionExecutorImpl(
    instruction: ReferenceInstruction,
    store: Store,
    stack: Stack,
): Result<Unit, InvocationError> =
    ReferenceInstructionExecutorImpl(
        instruction = instruction,
        store = store,
        stack = stack,
        refNullExecutor = ::RefNullExecutorImpl,
        refIsNullExecutor = ::RefIsNullExecutorImpl,
        refFuncExecutor = ::RefFuncExecutorImpl,
        refAsNonNullExecutor = ::RefAsNonNullExecutorImpl,
        refEqExecutor = ::RefEqExecutorImpl,
        refTestExecutor = ::RefTestExecutorImpl,
        refCastExecutor = ::RefCastExecutorImpl,
    )

internal fun ReferenceInstructionExecutorImpl(
    instruction: ReferenceInstruction,
    store: Store,
    stack: Stack,
    refNullExecutor: RefNullExecutor,
    refIsNullExecutor: RefIsNullExecutor,
    refFuncExecutor: RefFuncExecutor,
    refAsNonNullExecutor: RefAsNonNullExecutor,
    refEqExecutor: RefEqExecutor,
    refTestExecutor: RefTestExecutor,
    refCastExecutor: RefCastExecutor,
): Result<Unit, InvocationError> = binding {
    when (instruction) {
        is ReferenceInstruction.RefNull -> refNullExecutor(stack, instruction).bind()
        is ReferenceInstruction.RefIsNull -> refIsNullExecutor(stack).bind()
        is ReferenceInstruction.RefFunc -> refFuncExecutor(stack, instruction).bind()
        is ReferenceInstruction.RefAsNonNull -> refAsNonNullExecutor(stack).bind()
        is ReferenceInstruction.RefEq -> refEqExecutor(stack).bind()
        is ReferenceInstruction.RefTest -> refTestExecutor(store, stack, instruction.referenceType).bind()
        is ReferenceInstruction.RefCast -> refCastExecutor(store, stack, instruction.referenceType).bind()

        else -> Err(InvocationError.UnimplementedInstruction(ModuleInstruction(instruction))).bind<Unit>()
    }
}
