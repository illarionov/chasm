@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.memory.load

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.MemArg
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.memory
import io.github.charlietap.chasm.executor.runtime.ext.memoryAddress
import io.github.charlietap.chasm.executor.runtime.ext.peekFrame
import io.github.charlietap.chasm.executor.runtime.ext.popI32
import io.github.charlietap.chasm.executor.runtime.store.Store
import io.github.charlietap.chasm.executor.runtime.value.NumberValue

internal inline fun <S, U> LoadUnsignedNumberValueExecutorImpl(
    store: Store,
    stack: Stack,
    memArg: MemArg,
    sizeInBytes: Int,
    crossinline reader: NumberValueReader<U>,
    crossinline transformer: (U) -> S,
    crossinline constructor: (S) -> NumberValue<S>,
): Result<Unit, InvocationError> = binding {
    val frame = stack.peekFrame().bind()
    val memoryAddress = frame.state.module.memoryAddress(0).bind()
    val memory = store.memory(memoryAddress).bind()

    val baseAddress = stack.popI32().bind()

    val effectiveAddress = baseAddress + memArg.offset.toInt()

    val result = reader(memory, effectiveAddress, sizeInBytes).bind()
    val value = constructor(transformer(result))

    stack.push(Stack.Entry.Value(value))
}
