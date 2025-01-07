package io.github.charlietap.chasm.fixture.executor.runtime.instance

import io.github.charlietap.chasm.executor.runtime.instance.ExternalValue
import io.github.charlietap.chasm.executor.runtime.store.Address

fun externalValue(): ExternalValue = functionExternalValue()

fun functionExternalValue(
    address: Address.Function = functionAddress(),
) = ExternalValue.Function(
    address = address,
)

fun tableExternalValue(
    address: Address.Table = tableAddress(),
) = ExternalValue.Table(
    address = address,
)

fun memoryExternalValue(
    address: Address.Memory = memoryAddress(),
) = ExternalValue.Memory(
    address = address,
)

fun globalExternalValue(
    address: Address.Global = globalAddress(),
) = ExternalValue.Global(
    address = address,
)

fun tagExternalValue(
    address: Address.Tag = tagAddress(),
) = ExternalValue.Tag(
    address = address,
)
