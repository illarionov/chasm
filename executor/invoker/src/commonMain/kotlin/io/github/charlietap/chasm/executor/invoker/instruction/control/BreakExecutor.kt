package io.github.charlietap.chasm.executor.invoker.instruction.control

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.ast.module.Index
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError

internal typealias BreakExecutor = (Stack, Index.LabelIndex) -> Result<Unit, InvocationError>
