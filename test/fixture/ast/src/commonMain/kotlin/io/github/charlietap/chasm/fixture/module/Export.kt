package io.github.charlietap.chasm.fixture.module

import io.github.charlietap.chasm.ast.instruction.Index
import io.github.charlietap.chasm.ast.module.Export
import io.github.charlietap.chasm.ast.value.NameValue

fun functionExportDescriptor(
    functionIndex: Index.FunctionIndex = Index.FunctionIndex(0u),
) = Export.Descriptor.Function(
    functionIndex = functionIndex,
)

fun tableExportDescriptor(
    tableIndex: Index.TableIndex = Index.TableIndex(0u),
) = Export.Descriptor.Table(
    tableIndex = tableIndex,
)

fun memoryExportDescriptor(
    memoryIndex: Index.MemoryIndex = Index.MemoryIndex(0u),
) = Export.Descriptor.Memory(
    memoryIndex = memoryIndex,
)

fun globalExportDescriptor(
    globalIndex: Index.GlobalIndex = Index.GlobalIndex(0u),
) = Export.Descriptor.Global(
    globalIndex = globalIndex,
)

fun export(
    name: NameValue = NameValue(""),
    descriptor: Export.Descriptor = functionExportDescriptor(),
) = Export(
    name = name,
    descriptor = descriptor,
)
