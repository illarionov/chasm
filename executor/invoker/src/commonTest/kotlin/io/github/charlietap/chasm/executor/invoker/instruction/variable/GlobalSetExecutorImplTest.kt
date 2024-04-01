package io.github.charlietap.chasm.executor.invoker.instruction.variable

import com.github.michaelbull.result.Ok
import io.github.charlietap.chasm.ast.instruction.VariableInstruction
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.value.NumberValue
import io.github.charlietap.chasm.fixture.frame
import io.github.charlietap.chasm.fixture.instance.globalAddress
import io.github.charlietap.chasm.fixture.instance.globalInstance
import io.github.charlietap.chasm.fixture.instance.moduleInstance
import io.github.charlietap.chasm.fixture.module.globalIndex
import io.github.charlietap.chasm.fixture.stack
import io.github.charlietap.chasm.fixture.store
import io.github.charlietap.chasm.fixture.value
import kotlin.test.Test
import kotlin.test.assertEquals

class GlobalSetExecutorImplTest {

    @Test
    fun `can execute a global set instruction`() {

        val executionValue = NumberValue.I32(0)
        val globalIndex = globalIndex()
        val globalInstance = globalInstance(
            value = executionValue,
        )
        val globalAddress = globalAddress()
        val moduleInstance = moduleInstance(
            globalAddresses = mutableListOf(globalAddress),
        )
        val store = store(
            globals = mutableListOf(
                globalInstance,
            ),
        )
        val stack = stack()

        val frame = frame(
            state = Stack.Entry.ActivationFrame.State(
                locals = mutableListOf(),
                module = moduleInstance,
            ),
        )

        stack.push(frame)

        val expected = NumberValue.I32(117)
        stack.push(value(expected))

        val instruction = VariableInstruction.GlobalSet(globalIndex)

        val actual = GlobalSetExecutorImpl(
            instruction = instruction,
            store = store,
            stack = stack,
        )

        assertEquals(Ok(Unit), actual)
        assertEquals(1, stack.framesDepth())
        assertEquals(0, stack.valuesDepth())
        assertEquals(expected, store.globals[globalAddress.address].value)
    }
}
