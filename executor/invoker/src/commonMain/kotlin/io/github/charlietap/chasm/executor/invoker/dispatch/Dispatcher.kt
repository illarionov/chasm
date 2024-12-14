package io.github.charlietap.chasm.executor.invoker.dispatch

import io.github.charlietap.chasm.executor.runtime.dispatch.DispatchableInstruction

typealias Dispatcher<T> = (T) -> DispatchableInstruction
