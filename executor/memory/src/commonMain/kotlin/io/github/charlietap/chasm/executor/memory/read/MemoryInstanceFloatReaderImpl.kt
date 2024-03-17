@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.memory.read

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.executor.memory.ByteArrayLinearMemory
import io.github.charlietap.chasm.executor.memory.LinearMemoryInteractor
import io.github.charlietap.chasm.executor.memory.LinearMemoryInteractorImpl
import io.github.charlietap.chasm.executor.memory.ext.toFloatLittleEndian
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.instance.MemoryInstance

fun MemoryInstanceFloatReaderImpl(
    instance: MemoryInstance,
    byteOffsetInMemory: Int,
    valueSizeInBytes: Int,
): Result<Float, InvocationError.MemoryOperationOutOfBounds> =
    MemoryInstanceFloatReaderImpl(
        instance = instance,
        byteOffsetInMemory = byteOffsetInMemory,
        valueSizeInBytes = valueSizeInBytes,
        linearMemoryInteractor = ::LinearMemoryInteractorImpl,
    )

internal inline fun MemoryInstanceFloatReaderImpl(
    instance: MemoryInstance,
    byteOffsetInMemory: Int,
    valueSizeInBytes: Int,
    linearMemoryInteractor: LinearMemoryInteractor<Float>,
): Result<Float, InvocationError.MemoryOperationOutOfBounds> = linearMemoryInteractor(instance.data, byteOffsetInMemory, valueSizeInBytes) {
    (instance.data as ByteArrayLinearMemory).memory.toFloatLittleEndian(byteOffsetInMemory)
}
