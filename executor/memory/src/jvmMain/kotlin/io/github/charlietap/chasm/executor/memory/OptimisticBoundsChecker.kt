package io.github.charlietap.chasm.executor.memory

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.executor.runtime.error.InvocationError

actual inline fun <T> OptimisticBoundsChecker(
    address: Int,
    bytes: Int,
    memoryUpperBound: Int,
    crossinline operation: () -> T,
): Result<T, InvocationError.MemoryOperationOutOfBounds> {
    return try {
        Ok(operation())
    } catch (_: IndexOutOfBoundsException) {
        Err(InvocationError.MemoryOperationOutOfBounds)
    } catch (_: IllegalArgumentException) {
        Err(InvocationError.MemoryOperationOutOfBounds)
    }
}
