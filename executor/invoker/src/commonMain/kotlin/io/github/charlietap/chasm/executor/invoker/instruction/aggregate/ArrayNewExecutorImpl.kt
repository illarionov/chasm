@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.aggregate

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.module.Index
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.peekValue
import io.github.charlietap.chasm.executor.runtime.ext.popI32
import io.github.charlietap.chasm.executor.runtime.store.Store

internal fun ArrayNewExecutorImpl(
    store: Store,
    stack: Stack,
    typeIndex: Index.TypeIndex,
): Result<Unit, InvocationError> =
    ArrayNewExecutorImpl(
        store = store,
        stack = stack,
        typeIndex = typeIndex,
        arrayNewFixedExecutor = ::ArrayNewFixedExecutorImpl,
    )

internal inline fun ArrayNewExecutorImpl(
    store: Store,
    stack: Stack,
    typeIndex: Index.TypeIndex,
    crossinline arrayNewFixedExecutor: ArrayNewFixedExecutor,
): Result<Unit, InvocationError> = binding {

    val size = stack.popI32().bind()
    val value = stack.peekValue().bind()
    repeat(size - 1) {
        stack.push(value)
    }

    arrayNewFixedExecutor(store, stack, typeIndex, size.toUInt())
}
