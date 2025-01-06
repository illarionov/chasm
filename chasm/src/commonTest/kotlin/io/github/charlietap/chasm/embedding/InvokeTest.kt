package io.github.charlietap.chasm.embedding

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import io.github.charlietap.chasm.embedding.error.ChasmError
import io.github.charlietap.chasm.embedding.fixture.publicInstance
import io.github.charlietap.chasm.embedding.fixture.publicStore
import io.github.charlietap.chasm.embedding.shapes.ChasmResult
import io.github.charlietap.chasm.executor.invoker.FunctionInvoker
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.value.ExecutionValue
import io.github.charlietap.chasm.fixture.ast.value.nameValue
import io.github.charlietap.chasm.fixture.executor.runtime.instance.exportInstance
import io.github.charlietap.chasm.fixture.executor.runtime.instance.functionAddress
import io.github.charlietap.chasm.fixture.executor.runtime.instance.functionExternalValue
import io.github.charlietap.chasm.fixture.executor.runtime.instance.moduleInstance
import io.github.charlietap.chasm.fixture.executor.runtime.value.i32
import kotlin.test.Test
import kotlin.test.assertEquals

class InvokeTest {

    @Test
    fun `calling invoke on an instance with a function that is not exported returns FunctionNotFound`() {

        val function = "missing"

        val store = publicStore()
        val instance = publicInstance()

        val expected = ChasmError.ExecutionError(InvocationError.FunctionNotFound(function).toString())

        val actual = invoke(store, instance, function)

        assertEquals(ChasmResult.Error(expected), actual)
    }

    @Test
    fun `calling invoke on a function that fails propagates the invocation error`() {

        val function = "fail"

        val store = publicStore()
        val args = emptyList<ExecutionValue>()
        val address = functionAddress(0)
        val externalValue = functionExternalValue(address)
        val exportInstance = exportInstance(
            name = nameValue(function),
            value = externalValue,
        )
        val instance = publicInstance(
            moduleInstance(
                exports = mutableListOf(
                    exportInstance,
                ),
            ),
        )

        val error = InvocationError.Trap.TrapEncountered
        val functionInvoker: FunctionInvoker = { _store, _address, _args ->
            assertEquals(store.store, _store)
            assertEquals(address, _address)
            assertEquals(emptyList(), _args)

            Err(error)
        }

        val expected = ChasmError.ExecutionError(error.toString())

        val actual = invoke(store, instance, function, args, functionInvoker)

        assertEquals(ChasmResult.Error(expected), actual)
    }

    @Test
    fun `calling invoke on a function that succeeds propagates the results`() {

        val function = "succeed"

        val store = publicStore()
        val args = emptyList<ExecutionValue>()
        val address = functionAddress(0)
        val externalValue = functionExternalValue(address)
        val exportInstance = exportInstance(
            name = nameValue(function),
            value = externalValue,
        )
        val instance = publicInstance(
            moduleInstance(
                exports = mutableListOf(
                    exportInstance,
                ),
            ),
        )

        val results = listOf(i32(117))
        val functionInvoker: FunctionInvoker = { _store, _address, _args ->
            assertEquals(store.store, _store)
            assertEquals(address, _address)
            assertEquals(emptyList(), _args)

            Ok(results)
        }

        val expected = ChasmResult.Success(listOf(i32(117)))

        val actual = invoke(store, instance, function, args, functionInvoker)

        assertEquals(expected, actual)
    }
}
