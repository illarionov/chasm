package io.github.charlietap.chasm.executor.instantiator.predecoding.instruction.memory.store

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.MemoryInstruction
import io.github.charlietap.chasm.executor.instantiator.context.InstantiationContext
import io.github.charlietap.chasm.executor.invoker.dispatch.Dispatcher
import io.github.charlietap.chasm.executor.invoker.dispatch.memory.F64StoreDispatcher
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.error.InstantiationError
import io.github.charlietap.chasm.executor.runtime.error.ModuleTrapError
import io.github.charlietap.chasm.executor.runtime.ext.memory
import io.github.charlietap.chasm.executor.runtime.ext.memoryAddress
import io.github.charlietap.chasm.executor.runtime.instruction.MemoryInstruction.F64Store

internal fun F64StoreInstructionPredecoder(
    context: InstantiationContext,
    instruction: MemoryInstruction.F64Store,
): Result<DispatchableInstruction, ModuleTrapError> =
    F64StoreInstructionPredecoder(
        context = context,
        instruction = instruction,
        dispatcher = ::F64StoreDispatcher,
    )

internal inline fun F64StoreInstructionPredecoder(
    context: InstantiationContext,
    instruction: MemoryInstruction.F64Store,
    crossinline dispatcher: Dispatcher<F64Store>,
): Result<DispatchableInstruction, ModuleTrapError> = binding {
    val memoryAddress = context.instance?.memoryAddress(instruction.memoryIndex)?.bind()
        ?: Err(InstantiationError.PredecodingError).bind()
    val memory = context.store.memory(memoryAddress).bind()

    dispatcher(F64Store(memory, instruction.memArg))
}
