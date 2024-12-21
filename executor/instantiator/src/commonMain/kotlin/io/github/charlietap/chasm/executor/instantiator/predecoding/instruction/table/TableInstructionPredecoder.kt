package io.github.charlietap.chasm.executor.instantiator.predecoding.instruction.table

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.TableInstruction
import io.github.charlietap.chasm.executor.instantiator.context.InstantiationContext
import io.github.charlietap.chasm.executor.instantiator.predecoding.Predecoder
import io.github.charlietap.chasm.executor.invoker.dispatch.Dispatcher
import io.github.charlietap.chasm.executor.invoker.dispatch.table.ElemDropDispatcher
import io.github.charlietap.chasm.executor.invoker.dispatch.table.TableFillDispatcher
import io.github.charlietap.chasm.executor.invoker.dispatch.table.TableSizeDispatcher
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.error.ModuleTrapError
import io.github.charlietap.chasm.executor.runtime.instruction.TableInstruction.ElemDrop
import io.github.charlietap.chasm.executor.runtime.instruction.TableInstruction.TableFill
import io.github.charlietap.chasm.executor.runtime.instruction.TableInstruction.TableSize

internal fun TableInstructionPredecoder(
    context: InstantiationContext,
    instruction: TableInstruction,
): Result<DispatchableInstruction, ModuleTrapError> =
    TableInstructionPredecoder(
        context = context,
        instruction = instruction,
        tableGetPredecoder = ::TableGetInstructionPredecoder,
        tableSetPredecoder = ::TableSetInstructionPredecoder,
        tableInitPredecoder = ::TableInitInstructionPredecoder,
        elemDropDispatcher = ::ElemDropDispatcher,
        tableCopyPredecoder = ::TableCopyInstructionPredecoder,
        tableGrowPredecoder = ::TableGrowInstructionPredecoder,
        tableSizeDispatcher = ::TableSizeDispatcher,
        tableFillDispatcher = ::TableFillDispatcher,
    )

internal inline fun TableInstructionPredecoder(
    context: InstantiationContext,
    instruction: TableInstruction,
    crossinline tableGetPredecoder: Predecoder<TableInstruction.TableGet, DispatchableInstruction>,
    crossinline tableSetPredecoder: Predecoder<TableInstruction.TableSet, DispatchableInstruction>,
    crossinline tableInitPredecoder: Predecoder<TableInstruction.TableInit, DispatchableInstruction>,
    crossinline elemDropDispatcher: Dispatcher<ElemDrop>,
    crossinline tableCopyPredecoder: Predecoder<TableInstruction.TableCopy, DispatchableInstruction>,
    crossinline tableGrowPredecoder: Predecoder<TableInstruction.TableGrow, DispatchableInstruction>,
    crossinline tableSizeDispatcher: Dispatcher<TableSize>,
    crossinline tableFillDispatcher: Dispatcher<TableFill>,
): Result<DispatchableInstruction, ModuleTrapError> = binding {
    when (instruction) {
        is TableInstruction.TableGet -> tableGetPredecoder(context, instruction).bind()
        is TableInstruction.TableSet -> tableSetPredecoder(context, instruction).bind()
        is TableInstruction.TableInit -> tableInitPredecoder(context, instruction).bind()
        is TableInstruction.ElemDrop -> elemDropDispatcher(ElemDrop(instruction.elemIdx))
        is TableInstruction.TableCopy -> tableCopyPredecoder(context, instruction).bind()
        is TableInstruction.TableGrow -> tableGrowPredecoder(context, instruction).bind()
        is TableInstruction.TableSize -> tableSizeDispatcher(TableSize(instruction.tableIdx))
        is TableInstruction.TableFill -> tableFillDispatcher(TableFill(instruction.tableIdx))
    }
}
