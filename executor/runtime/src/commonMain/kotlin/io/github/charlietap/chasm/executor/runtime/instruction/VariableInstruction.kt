package io.github.charlietap.chasm.executor.runtime.instruction

import io.github.charlietap.chasm.ast.module.Index.GlobalIndex
import io.github.charlietap.chasm.ast.module.Index.LocalIndex
import kotlin.jvm.JvmInline

sealed interface VariableInstruction : ExecutionInstruction {
    @JvmInline
    value class LocalGet(val localIdx: LocalIndex) : VariableInstruction

    @JvmInline
    value class LocalSet(val localIdx: LocalIndex) : VariableInstruction

    @JvmInline
    value class LocalTee(val localIdx: LocalIndex) : VariableInstruction

    @JvmInline
    value class GlobalGet(val globalIdx: GlobalIndex) : VariableInstruction

    @JvmInline
    value class GlobalSet(val globalIdx: GlobalIndex) : VariableInstruction
}
