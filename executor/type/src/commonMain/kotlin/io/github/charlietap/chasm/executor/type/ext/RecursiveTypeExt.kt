@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.type.ext

import io.github.charlietap.chasm.ast.type.DefinedType
import io.github.charlietap.chasm.ast.type.RecursiveType
import io.github.charlietap.chasm.executor.type.rolling.DefinedTypeRoller
import io.github.charlietap.chasm.executor.type.rolling.DefinedTypeRollerImpl

inline fun RecursiveType.definedType(
    definedTypeRoller: DefinedTypeRoller = ::DefinedTypeRollerImpl,
): DefinedType = definedTypeRoller(0, this).first()
