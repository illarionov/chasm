package io.github.charlietap.chasm.executor.invoker.instruction.aggregate

import com.github.michaelbull.result.Ok
import io.github.charlietap.chasm.ast.type.AbstractHeapType
import io.github.charlietap.chasm.executor.runtime.ext.pushValue
import io.github.charlietap.chasm.executor.runtime.value.ReferenceValue
import io.github.charlietap.chasm.fixture.stack
import io.github.charlietap.chasm.fixture.value.externReferenceValue
import io.github.charlietap.chasm.fixture.value.nullReferenceValue
import io.github.charlietap.chasm.fixture.value.referenceValue
import kotlin.test.Test
import kotlin.test.assertEquals

class AnyConvertExternExecutorTest {

    @Test
    fun `can execute the AnyConvertExtern instruction when the value is ref null and return a correct result`() {

        val stack = stack()
        val nullRef = nullReferenceValue()

        stack.pushValue(nullRef)

        val actual = AnyConvertExternExecutor(stack)

        assertEquals(Ok(Unit), actual)
        assertEquals(ReferenceValue.Null(AbstractHeapType.Any), stack.popValueOrNull()?.value)
    }

    @Test
    fun `can execute the AnyConvertExtern instruction when the value is extern ref and return a correct result`() {

        val stack = stack()
        val referenceValue = referenceValue()
        val externReferenceValue = externReferenceValue(referenceValue)

        stack.pushValue(externReferenceValue)

        val actual = AnyConvertExternExecutor(stack)

        assertEquals(Ok(Unit), actual)
        assertEquals(referenceValue, stack.popValueOrNull()?.value)
    }
}