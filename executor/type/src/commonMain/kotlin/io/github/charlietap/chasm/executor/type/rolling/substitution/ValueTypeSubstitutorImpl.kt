package io.github.charlietap.chasm.executor.type.rolling.substitution

import io.github.charlietap.chasm.ast.type.ReferenceType
import io.github.charlietap.chasm.ast.type.ValueType

internal fun ValueTypeSubstitutorImpl(
    valueType: ValueType,
    concreteHeapTypeSubstitutor: ConcreteHeapTypeSubstitutor,
): ValueType =
    ValueTypeSubstitutorImpl(
        valueType = valueType,
        concreteHeapTypeSubstitutor = concreteHeapTypeSubstitutor,
        referenceTypeSubstitutor = ::ReferenceTypeSubstitutorImpl,
    )

internal fun ValueTypeSubstitutorImpl(
    valueType: ValueType,
    concreteHeapTypeSubstitutor: ConcreteHeapTypeSubstitutor,
    referenceTypeSubstitutor: TypeSubstitutor<ReferenceType>,
): ValueType = when (valueType) {
    is ValueType.Number,
    is ValueType.Vector,
    -> valueType
    is ValueType.Reference -> {
        ValueType.Reference(
            referenceTypeSubstitutor(
                valueType.referenceType,
                concreteHeapTypeSubstitutor,
            ),
        )
    }
}
