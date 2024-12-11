package io.github.charlietap.chasm.executor.invoker.ext

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.Stack.Companion.MAX_DEPTH
import io.github.charlietap.chasm.executor.runtime.error.InvocationError

inline fun Stack.pushFrame(frame: Stack.Entry.ActivationFrame): Result<Unit, InvocationError> {
    return if (framesDepth() < MAX_DEPTH) {
        push(frame)
        Ok(Unit)
    } else {
        Err(InvocationError.CallStackExhausted)
    }
}