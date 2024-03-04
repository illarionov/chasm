package io.github.charlietap.chasm.ext

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.toResultOr
import io.github.charlietap.chasm.ast.module.Version
import io.github.charlietap.chasm.error.WasmDecodeError

internal val MAGIC_NUMBER = ubyteArrayOf(0x00u, 0x61u, 0x73u, 0x6Du)

fun UByteArray.magicNumber() = if (contentEquals(MAGIC_NUMBER)) {
    Ok(Unit)
} else {
    Err(WasmDecodeError.InvalidWasmFile(this))
}

fun UByteArray.version() = Version.entries.firstOrNull { version ->
    contentEquals(version.number)
}.toResultOr { WasmDecodeError.UnsupportedVersion(this) }
