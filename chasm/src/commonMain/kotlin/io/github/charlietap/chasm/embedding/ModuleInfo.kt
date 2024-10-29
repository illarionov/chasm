package io.github.charlietap.chasm.embedding

import io.github.charlietap.chasm.ast.module.Export
import io.github.charlietap.chasm.ast.module.Import
import io.github.charlietap.chasm.embedding.error.ChasmError
import io.github.charlietap.chasm.embedding.shapes.ChasmResult
import io.github.charlietap.chasm.embedding.shapes.ExportDefinition
import io.github.charlietap.chasm.embedding.shapes.ExternalType
import io.github.charlietap.chasm.embedding.shapes.ImportDefinition
import io.github.charlietap.chasm.embedding.shapes.Module
import io.github.charlietap.chasm.embedding.shapes.ModuleInfo
import io.github.charlietap.chasm.embedding.transform.ExportDescriptorMapper
import io.github.charlietap.chasm.embedding.transform.ImportDescriptorMapper
import io.github.charlietap.chasm.embedding.transform.Mapper

fun moduleInfo(
    module: Module,
): ChasmResult<ModuleInfo, ChasmError.ExecutionError> = moduleInfo(
    module = module,
    importDescriptorMapper = ImportDescriptorMapper.instance,
    exportDescriptorMapper = ExportDescriptorMapper(module.module),
)

internal fun moduleInfo(
    module: Module,
    importDescriptorMapper: Mapper<Import.Descriptor, ExternalType>,
    exportDescriptorMapper: Mapper<Export.Descriptor, ExternalType>,
): ChasmResult<ModuleInfo, ChasmError.ExecutionError> {

    val internalModule = module.module

    val imports = internalModule.imports.map { import ->
        val type = importDescriptorMapper.map(import.descriptor)
        ImportDefinition(import.moduleName.name, import.entityName.name, type)
    }

    val exports = internalModule.exports.map { export ->
        val type = exportDescriptorMapper.map(export.descriptor)
        ExportDefinition(export.name.name, type)
    }

    return ChasmResult.Success(ModuleInfo(imports, exports))
}
