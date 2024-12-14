package io.github.charlietap.chasm.executor.invoker.function

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.expect
import io.github.charlietap.chasm.ast.type.AbstractHeapType
import io.github.charlietap.chasm.ast.type.ReferenceType
import io.github.charlietap.chasm.ast.type.ValueType
import io.github.charlietap.chasm.executor.invoker.dispatch.Dispatcher
import io.github.charlietap.chasm.executor.invoker.fixture.executionContext
import io.github.charlietap.chasm.executor.invoker.instruction.InstructionBlockExecutor
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.Stack.Entry.Instruction
import io.github.charlietap.chasm.executor.runtime.ext.default
import io.github.charlietap.chasm.executor.runtime.ext.pushFrame
import io.github.charlietap.chasm.executor.runtime.instance.FunctionInstance
import io.github.charlietap.chasm.fixture.ast.module.local
import io.github.charlietap.chasm.fixture.ast.type.functionType
import io.github.charlietap.chasm.fixture.ast.type.i32ValueType
import io.github.charlietap.chasm.fixture.ast.type.i64ValueType
import io.github.charlietap.chasm.fixture.ast.type.refNullReferenceType
import io.github.charlietap.chasm.fixture.ast.type.referenceValueType
import io.github.charlietap.chasm.fixture.ast.type.resultType
import io.github.charlietap.chasm.fixture.executor.runtime.dispatch.dispatchableInstruction
import io.github.charlietap.chasm.fixture.executor.runtime.frame
import io.github.charlietap.chasm.fixture.executor.runtime.function.runtimeExpression
import io.github.charlietap.chasm.fixture.executor.runtime.function.runtimeFunction
import io.github.charlietap.chasm.fixture.executor.runtime.instance.moduleInstance
import io.github.charlietap.chasm.fixture.executor.runtime.label
import io.github.charlietap.chasm.fixture.executor.runtime.returnArity
import io.github.charlietap.chasm.fixture.executor.runtime.stack
import io.github.charlietap.chasm.fixture.executor.runtime.store
import io.github.charlietap.chasm.fixture.executor.runtime.value.i32
import io.github.charlietap.chasm.type.ext.definedType
import kotlin.test.Test
import kotlin.test.assertEquals

class WasmFunctionCallTest {

    @Test
    fun `can execute a function call and return a result`() {

        val store = store()
        val stack = stack()
        val context = executionContext(
            store = store,
            stack = stack,
        )

        val functionType = functionType(
            params = resultType(
                listOf(
                    i32ValueType(),
                    i64ValueType(),
                ),
            ),
            results = resultType(
                listOf(
                    i32ValueType(),
                    i64ValueType(),
                ),
            ),
        )
        val definedType = functionType.definedType()

        val function = runtimeFunction(
            locals = listOf(
                local(
                    type = referenceValueType(
                        refNullReferenceType(AbstractHeapType.Func),
                    ),
                ),
            ),
            body = runtimeExpression(
                listOf(
                    dispatchableInstruction(),
                    dispatchableInstruction(),
                ),
            ),
        )

        val functionInstance = FunctionInstance.WasmFunction(
            type = definedType,
            module = moduleInstance(),
            function = function,
        )

        val label = label(
            arity = returnArity(functionType.params.types.size),
        )

        val params = listOf(
            i32(1),
            i32(2),
        )

        params.forEach { value ->
            stack.push(Stack.Entry.Value(value))
        }

        val locals = (
            params + function.locals.map { local ->
                local.type.default().expect { "Locals should have a default" }
            }
        ).toMutableList()

        val frame = frame(
            arity = returnArity(functionType.results.types.size),
            locals = locals,
            instance = functionInstance.module,
        )
        val frameDispatchable = dispatchableInstruction()
        val frameDispatcher: Dispatcher<Stack.Entry.ActivationFrame> = { frame ->
            frameDispatchable
        }
        val expectedFrameInstruction = Instruction(
            instruction = frameDispatchable,
            tag = Stack.Entry.InstructionTag.FRAME,
        )

        val instructionBlockExecutor: InstructionBlockExecutor = { _stack, _label, _instructions, _params, _handler ->
            assertEquals(stack, _stack)
            assertEquals(label, _label)
            assertEquals(function.body.instructions, _instructions)
            assertEquals(emptyList(), _params)

            assertEquals(frame, stack.peekFrameOrNull())
            assertEquals(expectedFrameInstruction, stack.peekInstructionOrNull())
            assertEquals(1, stack.framesDepth())

            Ok(Unit)
        }

        val actual = WasmFunctionCall(
            context = context,
            instance = functionInstance,
            instructionBlockExecutor = instructionBlockExecutor,
            frameDispatcher = frameDispatcher,
        )

        assertEquals(Ok(Unit), actual)
        assertEquals(1, stack.framesDepth())
    }

    @Test
    fun `can execute a tail recursive function call and return a result`() {

        val store = store()
        val stack = stack()
        val context = executionContext(
            store = store,
            stack = stack,
        )

        val functionType = functionType(
            params = resultType(
                listOf(
                    i32ValueType(),
                    i64ValueType(),
                ),
            ),
            results = resultType(
                listOf(
                    i32ValueType(),
                    i64ValueType(),
                ),
            ),
        )
        val definedType = functionType.definedType()

        val function = runtimeFunction(
            locals = listOf(
                local(type = ValueType.Reference(ReferenceType.RefNull(AbstractHeapType.Func))),
            ),
            body = runtimeExpression(
                listOf(
                    dispatchableInstruction(),
                    dispatchableInstruction(),
                ),
            ),
        )

        val functionInstance = FunctionInstance.WasmFunction(
            type = definedType,
            module = moduleInstance(),
            function = function,
        )

        val label = label(
            arity = returnArity(functionType.params.types.size),
        )

        val params = listOf(
            i32(1),
            i32(2),
        )

        params.forEach { value ->
            stack.push(Stack.Entry.Value(value))
        }

        val locals = (
            params + function.locals.map { local ->
                local.type.default().expect { "Locals should have a default" }
            }
        ).toMutableList()

        val frame = frame(
            arity = returnArity(functionType.results.types.size),
            locals = locals,
            instance = functionInstance.module,
        )
        val frameDispatchable = dispatchableInstruction()
        val frameDispatcher: Dispatcher<Stack.Entry.ActivationFrame> = { _frame ->
            assertEquals(frame, _frame)
            frameDispatchable
        }

        stack.pushFrame(frame)
        stack.push(
            Instruction(
                instruction = frameDispatchable,
                tag = Stack.Entry.InstructionTag.FRAME,
            ),
        )

        val instructionBlockExecutor: InstructionBlockExecutor = { _stack, _label, _instructions, _params, _handler ->
            assertEquals(stack, _stack)
            assertEquals(label, _label)
            assertEquals(function.body.instructions, _instructions)
            assertEquals(emptyList(), _params)

            assertEquals(frame, stack.peekFrameOrNull())
            assertEquals(1, stack.framesDepth())

            Ok(Unit)
        }

        val actual = ReturnWasmFunctionCall(
            context = context,
            instance = functionInstance,
            instructionBlockExecutor = instructionBlockExecutor,
            frameDispatcher = frameDispatcher,
        )

        assertEquals(Ok(Unit), actual)
        assertEquals(1, stack.framesDepth())
    }
}
