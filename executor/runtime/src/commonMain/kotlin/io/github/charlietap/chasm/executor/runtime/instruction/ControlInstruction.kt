package io.github.charlietap.chasm.executor.runtime.instruction

import io.github.charlietap.chasm.ast.instruction.ControlInstruction.CatchHandler
import io.github.charlietap.chasm.ast.module.Index
import io.github.charlietap.chasm.ast.type.FunctionType
import io.github.charlietap.chasm.ast.type.ReferenceType
import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction
import io.github.charlietap.chasm.executor.runtime.instance.FunctionInstance
import kotlin.jvm.JvmInline

sealed interface ControlInstruction : ExecutionInstruction {

    data object Unreachable : ControlInstruction

    data object Nop : ControlInstruction

    data class Block(val functionType: FunctionType, val instructions: List<DispatchableInstruction>) : ControlInstruction

    data class Loop(val functionType: FunctionType, val instructions: List<DispatchableInstruction>) : ControlInstruction

    data class If(val functionType: FunctionType, val thenInstructions: List<DispatchableInstruction>, val elseInstructions: List<DispatchableInstruction>?) : ControlInstruction

    data class TryTable(val functionType: FunctionType, val handlers: List<CatchHandler>, val instructions: List<DispatchableInstruction>) : ControlInstruction

    @JvmInline
    value class Throw(val tagIndex: Index.TagIndex) : ControlInstruction

    data object ThrowRef : ControlInstruction

    @JvmInline
    value class Br(val labelIndex: Index.LabelIndex) : ControlInstruction

    @JvmInline
    value class BrIf(val labelIndex: Index.LabelIndex) : ControlInstruction

    data class BrTable(val labelIndices: List<Index.LabelIndex>, val defaultLabelIndex: Index.LabelIndex) : ControlInstruction

    @JvmInline
    value class BrOnNull(val labelIndex: Index.LabelIndex) : ControlInstruction

    @JvmInline
    value class BrOnNonNull(val labelIndex: Index.LabelIndex) : ControlInstruction

    data class BrOnCast(
        val labelIndex: Index.LabelIndex,
        val srcReferenceType: ReferenceType,
        val dstReferenceType: ReferenceType,
    ) : ControlInstruction

    data class BrOnCastFail(
        val labelIndex: Index.LabelIndex,
        val srcReferenceType: ReferenceType,
        val dstReferenceType: ReferenceType,
    ) : ControlInstruction

    data object Return : ControlInstruction

    @JvmInline
    value class ReturnWasmFunctionCall(val instance: FunctionInstance.WasmFunction) : ControlInstruction

    @JvmInline
    value class ReturnHostFunctionCall(val instance: FunctionInstance.HostFunction) : ControlInstruction

    @JvmInline
    value class ReturnCallRef(val typeIndex: Index.TypeIndex) : ControlInstruction

    @JvmInline
    value class WasmFunctionCall(val instance: FunctionInstance.WasmFunction) : ControlInstruction

    @JvmInline
    value class HostFunctionCall(val instance: FunctionInstance.HostFunction) : ControlInstruction

    @JvmInline
    value class CallRef(val typeIndex: Index.TypeIndex) : ControlInstruction

    data class CallIndirect(
        val typeIndex: Index.TypeIndex,
        val tableIndex: Index.TableIndex,
    ) : ControlInstruction

    data class ReturnCallIndirect(
        val typeIndex: Index.TypeIndex,
        val tableIndex: Index.TableIndex,
    ) : ControlInstruction
}
