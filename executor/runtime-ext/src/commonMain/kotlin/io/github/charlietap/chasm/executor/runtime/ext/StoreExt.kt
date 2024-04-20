@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.runtime.ext

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.instance.ArrayInstance
import io.github.charlietap.chasm.executor.runtime.instance.DataInstance
import io.github.charlietap.chasm.executor.runtime.instance.ElementInstance
import io.github.charlietap.chasm.executor.runtime.instance.FunctionInstance
import io.github.charlietap.chasm.executor.runtime.instance.GlobalInstance
import io.github.charlietap.chasm.executor.runtime.instance.MemoryInstance
import io.github.charlietap.chasm.executor.runtime.instance.StructInstance
import io.github.charlietap.chasm.executor.runtime.instance.TableInstance
import io.github.charlietap.chasm.executor.runtime.store.Address
import io.github.charlietap.chasm.executor.runtime.store.Store

inline fun Store.function(address: Address.Function): Result<FunctionInstance, InvocationError.StoreLookupFailed> {
    return functions.getOrNull(address.address)?.let(::Ok) ?: Err(InvocationError.StoreLookupFailed(address))
}

inline fun Store.table(address: Address.Table): Result<TableInstance, InvocationError.StoreLookupFailed> {
    return tables.getOrNull(address.address)?.let(::Ok) ?: Err(InvocationError.StoreLookupFailed(address))
}

inline fun Store.memory(address: Address.Memory): Result<MemoryInstance, InvocationError.StoreLookupFailed> {
    return memories.getOrNull(address.address)?.let(::Ok) ?: Err(InvocationError.StoreLookupFailed(address))
}

inline fun Store.global(address: Address.Global): Result<GlobalInstance, InvocationError.StoreLookupFailed> {
    return globals.getOrNull(address.address)?.let(::Ok) ?: Err(InvocationError.StoreLookupFailed(address))
}

inline fun Store.element(address: Address.Element): Result<ElementInstance, InvocationError.StoreLookupFailed> {
    return elements.getOrNull(address.address)?.let(::Ok) ?: Err(InvocationError.StoreLookupFailed(address))
}

inline fun Store.data(address: Address.Data): Result<DataInstance, InvocationError.StoreLookupFailed> {
    return data.getOrNull(address.address)?.let(::Ok) ?: Err(InvocationError.StoreLookupFailed(address))
}

inline fun Store.struct(address: Address.Struct): Result<StructInstance, InvocationError.StoreLookupFailed> {
    return structs.getOrNull(address.address)?.value?.let(::Ok) ?: Err(InvocationError.StoreLookupFailed(address))
}

inline fun Store.array(address: Address.Array): Result<ArrayInstance, InvocationError.StoreLookupFailed> {
    return arrays.getOrNull(address.address)?.value?.let(::Ok) ?: Err(InvocationError.StoreLookupFailed(address))
}
