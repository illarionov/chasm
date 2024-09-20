package io.github.charlietap.chasm.executor.invoker.instruction.parametric

import com.github.michaelbull.result.Ok
import io.github.charlietap.chasm.executor.invoker.fixture.executionContext
import io.github.charlietap.chasm.fixture.instruction.selectInstruction
import io.github.charlietap.chasm.fixture.stack
import io.github.charlietap.chasm.fixture.value
import io.github.charlietap.chasm.fixture.value.i32
import kotlin.test.Test
import kotlin.test.assertEquals

class SelectExecutorTest {

    @Test
    fun `can execute the select instruction and choose value 1`() {

        val stack = stack()
        val context = executionContext(stack)

        val value1 = value(i32(117))
        val value2 = value(i32(118))

        stack.push(value1)
        stack.push(value2)
        stack.push(value(i32(1)))

        val instruction = selectInstruction()

        val actual = SelectExecutor(
            context = context,
            instruction = instruction,
        )

        assertEquals(Ok(Unit), actual)
        assertEquals(1, stack.valuesDepth())
        assertEquals(value1, stack.popValueOrNull())
    }

    @Test
    fun `can execute the select instruction and choose value 2`() {

        val stack = stack()
        val context = executionContext(stack)

        val value1 = value(i32(117))
        val value2 = value(i32(118))

        stack.push(value1)
        stack.push(value2)
        stack.push(value(i32(0)))

        val instruction = selectInstruction()

        val actual = SelectExecutor(
            context = context,
            instruction = instruction,
        )

        assertEquals(Ok(Unit), actual)
        assertEquals(1, stack.valuesDepth())
        assertEquals(value2, stack.popValueOrNull())
    }
}
