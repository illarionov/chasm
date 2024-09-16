package io.github.charlietap.chasm.type.extremas

import io.github.charlietap.chasm.ast.type.CompositeType
import io.github.charlietap.chasm.ast.type.ConcreteHeapType
import io.github.charlietap.chasm.ast.type.DefinedType
import io.github.charlietap.chasm.ast.type.HeapType
import io.github.charlietap.chasm.type.expansion.DefinedTypeExpander

internal fun TopOfConcreteHeapType(
    type: ConcreteHeapType,
    types: List<DefinedType>,
): HeapType? = TopOfConcreteHeapType(
    type = type,
    types = types,
    definedTypeExpander = ::DefinedTypeExpander,
    topOfCompositeType = ::TopOfCompositeType,
)

internal fun TopOfConcreteHeapType(
    type: ConcreteHeapType,
    types: List<DefinedType>,
    definedTypeExpander: DefinedTypeExpander,
    topOfCompositeType: TopOf<CompositeType>,
): HeapType? = when (type) {
    is ConcreteHeapType.Defined -> topOfCompositeType(definedTypeExpander(type.definedType), types)
    is ConcreteHeapType.TypeIndex -> topOfCompositeType(definedTypeExpander(types[type.index.idx.toInt()]), types)
    is ConcreteHeapType.RecursiveTypeIndex -> null
}
