package io.github.charlietap.chasm.fixture.module

import io.github.charlietap.chasm.ast.module.Index
import io.github.charlietap.chasm.ast.module.Type
import io.github.charlietap.chasm.ast.type.RecursiveType
import io.github.charlietap.chasm.fixture.type.recursiveType

fun type(
    idx: Index.TypeIndex = typeIndex(),
    recursiveType: RecursiveType = recursiveType(),
) = Type(
    idx = idx,
    recursiveType = recursiveType,
)
