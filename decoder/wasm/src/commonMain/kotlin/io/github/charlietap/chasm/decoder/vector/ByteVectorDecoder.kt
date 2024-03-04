package io.github.charlietap.chasm.decoder.vector

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.error.WasmDecodeError
import io.github.charlietap.chasm.reader.WasmBinaryReader

typealias ByteVectorDecoder = (WasmBinaryReader) -> Result<ByteVector, WasmDecodeError>
