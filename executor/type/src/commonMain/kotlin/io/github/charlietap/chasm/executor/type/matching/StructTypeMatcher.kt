package io.github.charlietap.chasm.executor.type.matching

import io.github.charlietap.chasm.ast.type.FieldType
import io.github.charlietap.chasm.ast.type.StructType

fun StructTypeMatcher(
    type1: StructType,
    type2: StructType,
    context: TypeMatcherContext,
): Boolean = StructTypeMatcher(
    type1 = type1,
    type2 = type2,
    context = context,
    fieldTypeMatcher = ::FieldTypeMatcher,
)

fun StructTypeMatcher(
    type1: StructType,
    type2: StructType,
    context: TypeMatcherContext,
    fieldTypeMatcher: TypeMatcher<FieldType>,
): Boolean = if (type1.fields.size >= type2.fields.size) {
    val type1Fields = type1.fields.take(type2.fields.size)
    type1Fields.zip(type2.fields).all { (field1, field2) -> fieldTypeMatcher(field1, field2, context) }
} else {
    false
}
