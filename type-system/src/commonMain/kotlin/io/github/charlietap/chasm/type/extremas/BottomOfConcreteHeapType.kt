package io.github.charlietap.chasm.type.extremas

import io.github.charlietap.chasm.ast.type.CompositeType
import io.github.charlietap.chasm.ast.type.ConcreteHeapType
import io.github.charlietap.chasm.ast.type.DefinedType
import io.github.charlietap.chasm.ast.type.HeapType
import io.github.charlietap.chasm.type.expansion.DefinedTypeExpander

internal fun BottomOfConcreteHeapType(
    type: ConcreteHeapType,
    types: List<DefinedType>,
): HeapType? = BottomOfConcreteHeapType(
    type = type,
    types = types,
    definedTypeExpander = ::DefinedTypeExpander,
    bottomOfCompositeType = ::BottomOfCompositeType,
)

internal fun BottomOfConcreteHeapType(
    type: ConcreteHeapType,
    types: List<DefinedType>,
    definedTypeExpander: DefinedTypeExpander,
    bottomOfCompositeType: BottomOf<CompositeType>,
): HeapType? = when (type) {
    is ConcreteHeapType.Defined -> bottomOfCompositeType(definedTypeExpander(type.definedType), types)
    is ConcreteHeapType.TypeIndex -> bottomOfCompositeType(definedTypeExpander(types[type.index.idx.toInt()]), types)
    is ConcreteHeapType.RecursiveTypeIndex -> null
}
