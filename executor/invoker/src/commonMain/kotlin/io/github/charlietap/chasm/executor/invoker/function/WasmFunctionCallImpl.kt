@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.function

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.executor.invoker.flow.ReturnException
import io.github.charlietap.chasm.executor.invoker.instruction.InstructionBlockExecutor
import io.github.charlietap.chasm.executor.invoker.instruction.InstructionBlockExecutorImpl
import io.github.charlietap.chasm.executor.runtime.Arity
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.default
import io.github.charlietap.chasm.executor.runtime.ext.popFrame
import io.github.charlietap.chasm.executor.runtime.ext.popLabel
import io.github.charlietap.chasm.executor.runtime.ext.popValue
import io.github.charlietap.chasm.executor.runtime.instance.FunctionInstance
import io.github.charlietap.chasm.executor.runtime.store.Store
import io.github.charlietap.chasm.executor.type.ext.functionType

internal inline fun WasmFunctionCallImpl(
    store: Store,
    stack: Stack,
    instance: FunctionInstance.WasmFunction,
    tailRecursion: Boolean,
): Result<Unit, InvocationError> =
    WasmFunctionCallImpl(
        store = store,
        stack = stack,
        instance = instance,
        tailRecursion = tailRecursion,
        instructionBlockExecutor = ::InstructionBlockExecutorImpl,
    )

internal inline fun WasmFunctionCallImpl(
    store: Store,
    stack: Stack,
    instance: FunctionInstance.WasmFunction,
    tailRecursion: Boolean,
    crossinline instructionBlockExecutor: InstructionBlockExecutor,
): Result<Unit, InvocationError> = binding {

    if (tailRecursion) {
        stack.popFrame().bind()
    }

    val type = instance.functionType().bind()

    val params = List(type.params.types.size) {
        stack.popValue().bind().value
    }.asReversed()

    val locals = params + instance.function.locals.map { local ->
        local.type.default().bind()
    }

    val frame = Stack.Entry.ActivationFrame(
        arity = Arity.Return(type.results.types.size),
        state = Stack.Entry.ActivationFrame.State(
            locals = locals.toMutableList(),
            module = instance.module,
        ),
    )

    stack.push(frame)

    val label = Stack.Entry.Label(
        arity = Arity.Return(type.results.types.size),
        continuation = emptyList(),
    )

    val labelsDepth = stack.labelsDepth()
    val valuesDepth = stack.valuesDepth()

    try {
        instructionBlockExecutor(store, stack, label, instance.function.body.instructions, emptyList()).bind()
    } catch (exception: ReturnException) {
        while (stack.labelsDepth() > labelsDepth) {
            stack.popLabel().bind()
        }
        while (stack.valuesDepth() > valuesDepth) {
            stack.popValue().bind()
        }

        exception.results.forEach { value ->
            stack.push(Stack.Entry.Value(value))
        }
    }

    if (!tailRecursion) {
        stack.popFrame().bind()
    }
}
