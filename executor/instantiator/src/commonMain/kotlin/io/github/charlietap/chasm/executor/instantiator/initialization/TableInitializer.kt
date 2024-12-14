package io.github.charlietap.chasm.executor.instantiator.initialization

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.Expression
import io.github.charlietap.chasm.ast.instruction.NumericInstruction
import io.github.charlietap.chasm.ast.instruction.TableInstruction
import io.github.charlietap.chasm.ast.module.ElementSegment
import io.github.charlietap.chasm.executor.instantiator.context.InstantiationContext
import io.github.charlietap.chasm.executor.instantiator.predecoding.ExpressionPredecoder
import io.github.charlietap.chasm.executor.instantiator.predecoding.Predecoder
import io.github.charlietap.chasm.executor.invoker.ExpressionEvaluator
import io.github.charlietap.chasm.executor.runtime.Arity
import io.github.charlietap.chasm.executor.runtime.error.ModuleTrapError
import io.github.charlietap.chasm.executor.runtime.instance.ModuleInstance
import io.github.charlietap.chasm.executor.runtime.function.Expression as RuntimeExpression

internal typealias TableInitializer = (InstantiationContext, ModuleInstance) -> Result<Unit, ModuleTrapError>

internal fun TableInitializer(
    context: InstantiationContext,
    instance: ModuleInstance,
): Result<Unit, ModuleTrapError> =
    TableInitializer(
        context = context,
        instance = instance,
        evaluator = ::ExpressionEvaluator,
        expressionPredecoder = ::ExpressionPredecoder,
    )

internal inline fun TableInitializer(
    context: InstantiationContext,
    instance: ModuleInstance,
    crossinline evaluator: ExpressionEvaluator,
    crossinline expressionPredecoder: Predecoder<Expression, RuntimeExpression>,
): Result<Unit, ModuleTrapError> = binding {

    val (store, module) = context
    module.elementSegments
        .filter { segment ->
            segment.mode is ElementSegment.Mode.Active
        }.forEach { segment ->
            val expressionCount = segment.initExpressions.size
            val mode = segment.mode as ElementSegment.Mode.Active
            val expression = Expression(
                instructions = mode.offsetExpr.instructions + listOf(
                    NumericInstruction.I32Const(0),
                    NumericInstruction.I32Const(expressionCount),
                    TableInstruction.TableInit(segment.idx, mode.tableIndex),
                    TableInstruction.ElemDrop(segment.idx),
                ),
            )
            val runtimeExpression = expressionPredecoder(context, expression).bind()
            evaluator(store, instance, runtimeExpression, Arity.Return.SIDE_EFFECT).bind()
        }

    module.elementSegments
        .filter { segment ->
            segment.mode is ElementSegment.Mode.Declarative
        }.forEach { segment ->
            val expression = Expression(
                instructions = listOf(
                    TableInstruction.ElemDrop(segment.idx),
                ),
            )
            val runtimeExpression = expressionPredecoder(context, expression).bind()
            evaluator(store, instance, runtimeExpression, Arity.Return.SIDE_EFFECT).bind()
        }
}
