@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.type.ext

import io.github.charlietap.chasm.ast.type.CompositeType
import io.github.charlietap.chasm.ast.type.DefinedType
import io.github.charlietap.chasm.ast.type.FunctionType
import io.github.charlietap.chasm.ast.type.RecursiveType
import io.github.charlietap.chasm.ast.type.SubType
import io.github.charlietap.chasm.executor.type.rolling.DefinedTypeRoller
import io.github.charlietap.chasm.executor.type.rolling.DefinedTypeRollerImpl

inline fun FunctionType.recursiveType() = RecursiveType(
    listOf(
        SubType.Final(emptyList(), CompositeType.Function(this)),
    ),
)

inline fun FunctionType.definedType(
    definedTypeRoller: DefinedTypeRoller = ::DefinedTypeRollerImpl,
): DefinedType = definedTypeRoller(0, this.recursiveType()).first()
