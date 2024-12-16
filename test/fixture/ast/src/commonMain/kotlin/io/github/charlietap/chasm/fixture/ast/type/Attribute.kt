package io.github.charlietap.chasm.fixture.ast.type

import io.github.charlietap.chasm.ast.type.TagType
import io.github.charlietap.chasm.ast.type.TagType.Attribute

fun attribute(): Attribute = exceptionAttribute()

fun exceptionAttribute() = TagType.Attribute.Exception