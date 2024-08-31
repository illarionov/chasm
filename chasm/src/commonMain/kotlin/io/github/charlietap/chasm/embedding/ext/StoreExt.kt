package io.github.charlietap.chasm.embedding.ext

import io.github.charlietap.chasm.embedding.error.ChasmError
import io.github.charlietap.chasm.embedding.memory.readByte
import io.github.charlietap.chasm.embedding.memory.readBytes
import io.github.charlietap.chasm.embedding.shapes.ChasmResult
import io.github.charlietap.chasm.embedding.shapes.Memory
import io.github.charlietap.chasm.embedding.shapes.Store
import io.github.charlietap.chasm.embedding.shapes.map

fun Store.byte(
    pointer: Int,
    memory: Memory,
): ChasmResult<Byte, ChasmError.ExecutionError> = readByte(this, memory, pointer)

fun Store.bytes(
    pointer: Int,
    numberOfBytes: Int,
    memory: Memory,
): ChasmResult<ByteArray, ChasmError.ExecutionError> = readBytes(this, memory, pointer, numberOfBytes)

fun Store.int(
    pointer: Int,
    memory: Memory,
): ChasmResult<Int, ChasmError.ExecutionError> = readBytes(this, memory, pointer, Int.SIZE_BYTES).map { bytes ->
    var result: Int = 0
    for (i in 0 until Int.SIZE_BYTES) {
        result = result or (bytes[i].toInt() shl Byte.SIZE_BITS * i)
    }
    result
}

fun Store.uint(
    pointer: Int,
    memory: Memory,
): ChasmResult<UInt, ChasmError.ExecutionError> = readBytes(this, memory, pointer, Int.SIZE_BYTES).map { bytes ->
    var result: UInt = 0u
    for (i in 0 until Int.SIZE_BYTES) {
        result = result or (bytes[i].toUInt() shl (Byte.SIZE_BITS * i))
    }
    result
}

fun Store.long(
    pointer: Int,
    memory: Memory,
): ChasmResult<Long, ChasmError.ExecutionError> = readBytes(this, memory, pointer, Long.SIZE_BYTES).map { bytes ->
    var result: Long = 0
    for (i in 0 until Long.SIZE_BYTES) {
        result = result or (bytes[i].toLong() shl Byte.SIZE_BITS * i)
    }
    result
}

fun Store.ulong(
    pointer: Int,
    memory: Memory,
): ChasmResult<ULong, ChasmError.ExecutionError> = readBytes(this, memory, pointer, Long.SIZE_BYTES).map { bytes ->
    var result: ULong = 0uL
    for (i in 0 until Long.SIZE_BYTES) {
        result = result or (bytes[i].toULong() shl (Byte.SIZE_BITS * i))
    }
    result
}
