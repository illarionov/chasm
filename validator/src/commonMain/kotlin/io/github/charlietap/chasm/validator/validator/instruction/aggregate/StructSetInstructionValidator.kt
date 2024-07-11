package io.github.charlietap.chasm.validator.validator.instruction.aggregate

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import com.github.michaelbull.result.toResultOr
import io.github.charlietap.chasm.ast.instruction.AggregateInstruction
import io.github.charlietap.chasm.ast.type.ConcreteHeapType
import io.github.charlietap.chasm.ast.type.Mutability
import io.github.charlietap.chasm.ast.type.ReferenceType
import io.github.charlietap.chasm.ast.type.ValueType
import io.github.charlietap.chasm.type.ext.structType
import io.github.charlietap.chasm.validator.context.ValidationContext
import io.github.charlietap.chasm.validator.error.ModuleValidatorError
import io.github.charlietap.chasm.validator.error.TypeValidatorError
import io.github.charlietap.chasm.validator.ext.fieldType
import io.github.charlietap.chasm.validator.ext.pop
import io.github.charlietap.chasm.validator.ext.type
import io.github.charlietap.chasm.validator.ext.unpack

internal fun StructSetInstructionValidator(
    context: ValidationContext,
    instruction: AggregateInstruction.StructSet,
): Result<Unit, ModuleValidatorError> = binding {

    val definedType = context.type(instruction.typeIndex).bind()
    val structType = definedType.structType().toResultOr {
        TypeValidatorError.TypeMismatch
    }.bind()

    val fieldType = structType.fieldType(instruction.fieldIndex).bind()
    if (fieldType.mutability != Mutability.Var) {
        Err(TypeValidatorError.TypeMismatch).bind<Unit>()
    }

    val expected = fieldType.unpack()
    context.pop(expected).bind()

    context.pop(ValueType.Reference(ReferenceType.RefNull(ConcreteHeapType.Defined(definedType))))
}
