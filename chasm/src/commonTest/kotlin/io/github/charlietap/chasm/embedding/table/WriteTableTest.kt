package io.github.charlietap.chasm.embedding.table

import io.github.charlietap.chasm.ChasmResult
import io.github.charlietap.chasm.ast.type.HeapType
import io.github.charlietap.chasm.error.ChasmError
import io.github.charlietap.chasm.executor.runtime.store.Address
import io.github.charlietap.chasm.executor.runtime.value.ReferenceValue
import io.github.charlietap.chasm.fixture.instance.functionAddress
import io.github.charlietap.chasm.fixture.instance.tableInstance
import io.github.charlietap.chasm.fixture.store
import kotlin.test.Test
import kotlin.test.assertEquals

class WriteTableTest {

    @Test
    fun `can write a reference value to a table`() {

        val functionAddress = functionAddress()
        val value = ReferenceValue.FunctionAddress(functionAddress)
        val instance = tableInstance(elements = mutableListOf(ReferenceValue.Null(HeapType.Func)))
        val store = store(tables = mutableListOf(instance))
        val address = Address.Table(0)

        val expected: ChasmResult<Unit, ChasmError.ExecutionError> = ChasmResult.Success(Unit)

        val actual = writeTable(
            store = store,
            address = address,
            elementIndex = 0,
            value = value,
        )

        assertEquals(expected, actual)
        assertEquals(value, store.tables[0].elements[0])
    }
}
