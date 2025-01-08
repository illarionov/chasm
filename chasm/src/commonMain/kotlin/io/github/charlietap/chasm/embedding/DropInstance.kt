package io.github.charlietap.chasm.embedding

import com.github.michaelbull.result.map
import io.github.charlietap.chasm.ast.type.SharedStatus
import io.github.charlietap.chasm.embedding.error.ChasmError
import io.github.charlietap.chasm.embedding.shapes.ChasmResult
import io.github.charlietap.chasm.embedding.shapes.ChasmResult.Success
import io.github.charlietap.chasm.embedding.shapes.Instance
import io.github.charlietap.chasm.embedding.shapes.Store
import io.github.charlietap.chasm.executor.invoker.drop.MemoryInstanceDropper
import io.github.charlietap.chasm.executor.runtime.ext.data
import io.github.charlietap.chasm.executor.runtime.ext.element
import io.github.charlietap.chasm.executor.runtime.ext.global
import io.github.charlietap.chasm.executor.runtime.ext.memory
import io.github.charlietap.chasm.executor.runtime.ext.table
import io.github.charlietap.chasm.executor.runtime.value.ExecutionValue

fun dropInstance(
    store: Store,
    instance: Instance,
): ChasmResult<Unit, ChasmError.ExecutionError> {
    return dropInstance(
        store = store,
        instance = instance,
        memoryDropper = ::MemoryInstanceDropper,
    )
}

internal fun dropInstance(
    store: Store,
    instance: Instance,
    memoryDropper: MemoryInstanceDropper,
): ChasmResult<Unit, ChasmError.ExecutionError> {

    val instance = instance.instance
    val store = store.store

    instance.dataAddresses.forEach { address ->
        store.data(address).map { data ->
            data.bytes = ubyteArrayOf()
        }
    }
    instance.dataAddresses.clear()

    instance.elemAddresses.forEach { address ->
        store.element(address).map { element ->
            element.elements = arrayOf()
        }
    }
    instance.elemAddresses.clear()

    instance.exports.clear()

    instance.functionAddresses.clear()

    val uninit = ExecutionValue.Uninitialised
    instance.globalAddresses.forEach { address ->
        store.global(address).map { global ->
            global.value = uninit
        }
    }
    instance.globalAddresses.clear()

    instance.memAddresses.forEach { address ->
        store.memory(address).map { memory ->
            if (memory.type.shared == SharedStatus.Unshared) {
                memoryDropper(memory)
            }
        }
    }
    instance.memAddresses.clear()

    instance.tableAddresses.forEach { address ->
        store.table(address).map { table ->
            table.elements = arrayOf()
        }
    }
    instance.tableAddresses.clear()

    instance.tagAddresses.clear()
    instance.deallocated = true

    return Success(Unit)
}
