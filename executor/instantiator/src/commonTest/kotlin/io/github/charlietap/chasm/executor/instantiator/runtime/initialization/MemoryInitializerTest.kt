package io.github.charlietap.chasm.executor.instantiator.runtime.initialization

import com.github.michaelbull.result.Ok
import io.github.charlietap.chasm.ast.instruction.Expression
import io.github.charlietap.chasm.executor.instantiator.initialization.MemoryInitializer
import io.github.charlietap.chasm.executor.instantiator.predecoding.Predecoder
import io.github.charlietap.chasm.executor.invoker.ExpressionEvaluator
import io.github.charlietap.chasm.executor.runtime.Arity
import io.github.charlietap.chasm.fixture.ast.instruction.dataDropInstruction
import io.github.charlietap.chasm.fixture.ast.instruction.expression
import io.github.charlietap.chasm.fixture.ast.instruction.i32ConstInstruction
import io.github.charlietap.chasm.fixture.ast.instruction.memoryInitInstruction
import io.github.charlietap.chasm.fixture.ast.module.activeDataSegmentMode
import io.github.charlietap.chasm.fixture.ast.module.dataIndex
import io.github.charlietap.chasm.fixture.ast.module.dataSegment
import io.github.charlietap.chasm.fixture.ast.module.memoryIndex
import io.github.charlietap.chasm.fixture.ast.module.module
import io.github.charlietap.chasm.fixture.executor.instantiator.instantiationContext
import io.github.charlietap.chasm.fixture.executor.runtime.function.runtimeExpression
import io.github.charlietap.chasm.fixture.executor.runtime.instance.moduleInstance
import io.github.charlietap.chasm.fixture.executor.runtime.store
import kotlin.test.Test
import kotlin.test.assertEquals
import io.github.charlietap.chasm.executor.runtime.function.Expression as RuntimeExpression

class MemoryInitializerTest {

    @Test
    fun `can initialize a memory on a module instance`() {

        val activeDataIndex = dataIndex(0u)
        val offset = 117
        val activeOffsetExpression = expression(
            listOf(
                i32ConstInstruction(offset),
            ),
        )
        val memoryIndex = memoryIndex(0u)
        val activeSegment = dataSegment(
            idx = activeDataIndex,
            mode = activeDataSegmentMode(
                memoryIndex = memoryIndex,
                offset = activeOffsetExpression,
            ),
        )

        val store = store()
        val module = module(
            dataSegments = listOf(activeSegment),
        )
        val context = instantiationContext(
            store = store,
            module = module,
        )
        val instance = moduleInstance()

        val expression = expression(
            listOf(
                i32ConstInstruction(offset),
                i32ConstInstruction(0),
                i32ConstInstruction(activeSegment.initData.size),
                memoryInitInstruction(memoryIndex, activeDataIndex),
                dataDropInstruction(activeDataIndex),
            ),
        )
        val runtimeExpression = runtimeExpression()
        val expressionPredecoder: Predecoder<Expression, RuntimeExpression> = { _context, _expression ->
            assertEquals(expression, _expression)
            Ok(runtimeExpression)
        }

        val evaluator: ExpressionEvaluator = { _store, _instance, _expression, _arity ->
            assertEquals(store, _store)
            assertEquals(instance, _instance)
            assertEquals(runtimeExpression, _expression)
            assertEquals(Arity.Return.SIDE_EFFECT, _arity)

            Ok(null)
        }

        val actual = MemoryInitializer(
            context = context,
            instance = instance,
            evaluator = evaluator,
            expressionPredecoder = expressionPredecoder,
        )

        assertEquals(Ok(Unit), actual)
    }
}
