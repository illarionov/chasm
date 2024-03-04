package io.github.charlietap.chasm.fixture.type

import io.github.charlietap.chasm.ast.type.FunctionType
import io.github.charlietap.chasm.ast.type.ResultType

fun functionType(
    params: ResultType = ResultType(emptyList()),
    results: ResultType = ResultType(emptyList()),
) = FunctionType(
    params,
    results,
)
