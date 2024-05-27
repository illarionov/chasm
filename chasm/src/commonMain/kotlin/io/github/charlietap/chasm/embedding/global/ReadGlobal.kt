package io.github.charlietap.chasm.embedding.global

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import com.github.michaelbull.result.fold
import com.github.michaelbull.result.mapError
import io.github.charlietap.chasm.ChasmResult
import io.github.charlietap.chasm.ChasmResult.Error
import io.github.charlietap.chasm.ChasmResult.Success
import io.github.charlietap.chasm.error.ChasmError
import io.github.charlietap.chasm.executor.runtime.error.ModuleTrapError
import io.github.charlietap.chasm.executor.runtime.ext.global
import io.github.charlietap.chasm.executor.runtime.store.Address
import io.github.charlietap.chasm.executor.runtime.store.Store
import io.github.charlietap.chasm.executor.runtime.value.ExecutionValue

fun readGlobal(
    store: Store,
    address: Address.Global,
): ChasmResult<ExecutionValue, ChasmError.ExecutionError> =
    internalReadGlobal(
        store = store,
        address = address,
    )
        .mapError(ChasmError::ExecutionError)
        .fold(::Success, ::Error)

internal fun internalReadGlobal(
    store: Store,
    address: Address.Global,
): Result<ExecutionValue, ModuleTrapError> = binding {
    val global = store.global(address).bind()
    global.value
}
