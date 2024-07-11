package io.github.charlietap.chasm.validator.validator.instruction.control

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import com.github.michaelbull.result.toResultOr
import io.github.charlietap.chasm.ast.instruction.ControlInstruction
import io.github.charlietap.chasm.ast.type.ConcreteHeapType
import io.github.charlietap.chasm.ast.type.ReferenceType
import io.github.charlietap.chasm.ast.type.ValueType
import io.github.charlietap.chasm.type.ext.functionType
import io.github.charlietap.chasm.validator.context.ValidationContext
import io.github.charlietap.chasm.validator.error.ModuleValidatorError
import io.github.charlietap.chasm.validator.error.TypeValidatorError
import io.github.charlietap.chasm.validator.ext.pop
import io.github.charlietap.chasm.validator.ext.popValues
import io.github.charlietap.chasm.validator.ext.pushValues
import io.github.charlietap.chasm.validator.ext.type
import io.github.charlietap.chasm.validator.ext.unreachable

internal fun ReturnCallRefInstructionValidator(
    context: ValidationContext,
    instruction: ControlInstruction.ReturnCallRef,
): Result<Unit, ModuleValidatorError> = binding {

    val definedType = context.type(instruction.typeIndex).bind()
    val functionType = definedType.functionType().toResultOr {
        TypeValidatorError.TypeMismatch
    }.bind()

    if (functionType.results != context.result) {
        Err(TypeValidatorError.TypeMismatch).bind<Unit>()
    }

    context.popValues(functionType.params.types).bind()
    context.pop(ValueType.Reference(ReferenceType.RefNull(ConcreteHeapType.Defined(definedType)))).bind()
    context.pushValues(functionType.results.types)

    context.popValues(context.result?.types?.asReversed() ?: emptyList()).bind()
    context.unreachable().bind()
}
