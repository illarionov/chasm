package io.github.charlietap.chasm.embedding

import io.github.charlietap.chasm.embedding.shapes.Store
import io.github.charlietap.chasm.embedding.shapes.Table
import io.github.charlietap.chasm.embedding.shapes.TableType
import io.github.charlietap.chasm.embedding.transform.Mapper
import io.github.charlietap.chasm.embedding.transform.TableTypeMapper
import io.github.charlietap.chasm.executor.instantiator.allocation.table.TableAllocator
import io.github.charlietap.chasm.executor.runtime.instance.ExternalValue
import io.github.charlietap.chasm.executor.runtime.value.ReferenceValue
import io.github.charlietap.chasm.ast.type.TableType as InternalTableType

fun table(
    store: Store,
    type: TableType,
    value: ReferenceValue,
): Table = table(
    store = store,
    type = type,
    value = value,
    allocator = ::TableAllocator,
    tableTypeMapper = TableTypeMapper.instance,
)

internal fun table(
    store: Store,
    type: TableType,
    value: ReferenceValue,
    allocator: TableAllocator,
    tableTypeMapper: Mapper<TableType, InternalTableType>,
): Table {
    val tableType = tableTypeMapper.map(type)
    return Table(ExternalValue.Table(allocator(store.store, tableType, value)))
}
