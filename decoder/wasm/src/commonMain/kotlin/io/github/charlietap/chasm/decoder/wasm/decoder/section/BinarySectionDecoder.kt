package io.github.charlietap.chasm.decoder.wasm.decoder.section

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.decoder.wasm.decoder.section.code.BinaryCodeSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.code.CodeSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.custom.BinaryCustomSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.custom.CustomSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.data.BinaryDataSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.data.DataSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.datacount.BinaryDataCountSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.datacount.DataCountSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.element.BinaryElementSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.element.ElementSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.export.BinaryExportSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.export.ExportSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.function.BinaryFunctionSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.function.FunctionSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.global.BinaryGlobalSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.global.GlobalSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.import.BinaryImportSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.import.ImportSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.memory.BinaryMemorySectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.memory.MemorySectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.start.BinaryStartSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.start.StartSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.table.BinaryTableSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.table.TableSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.type.BinaryTypeSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.section.type.TypeSectionDecoder
import io.github.charlietap.chasm.decoder.wasm.error.SectionDecodeError
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError
import io.github.charlietap.chasm.decoder.wasm.ext.trackBytes
import io.github.charlietap.chasm.decoder.wasm.reader.WasmBinaryReader
import io.github.charlietap.chasm.decoder.wasm.section.Section
import io.github.charlietap.chasm.decoder.wasm.section.SectionSize
import io.github.charlietap.chasm.decoder.wasm.section.SectionType

internal class BinarySectionDecoder(
    private val customSectionDecoder: CustomSectionDecoder = BinaryCustomSectionDecoder(),
    private val codeSectionDecoder: CodeSectionDecoder = BinaryCodeSectionDecoder(),
    private val dataSectionDecoder: DataSectionDecoder = BinaryDataSectionDecoder(),
    private val dataCountSectionDecoder: DataCountSectionDecoder = BinaryDataCountSectionDecoder(),
    private val elementSectionDecoder: ElementSectionDecoder = BinaryElementSectionDecoder(),
    private val importSectionDecoder: ImportSectionDecoder = BinaryImportSectionDecoder(),
    private val exportSectionDecoder: ExportSectionDecoder = BinaryExportSectionDecoder(),
    private val functionSectionDecoder: FunctionSectionDecoder = BinaryFunctionSectionDecoder(),
    private val globalSectionDecoder: GlobalSectionDecoder = BinaryGlobalSectionDecoder(),
    private val memorySectionDecoder: MemorySectionDecoder = BinaryMemorySectionDecoder(),
    private val startSectionDecoder: StartSectionDecoder = BinaryStartSectionDecoder(),
    private val tableSectionDecoder: TableSectionDecoder = BinaryTableSectionDecoder(),
    private val typeSectionDecoder: TypeSectionDecoder = BinaryTypeSectionDecoder(),
) : SectionDecoder {

    override fun invoke(
        reader: WasmBinaryReader,
        type: SectionType,
        size: SectionSize,
    ): Result<Section, WasmDecodeError> = binding {

        val (section, bytesConsumed) = reader.trackBytes {
            decodeSection(reader, type, size).bind()
        }

        if (size.size != bytesConsumed) {
            Err(SectionDecodeError.SectionSizeMismatch).bind<Unit>()
        }

        section
    }

    private fun decodeSection(
        reader: WasmBinaryReader,
        type: SectionType,
        size: SectionSize,
    ): Result<Section, WasmDecodeError> = when (type) {
        SectionType.Custom -> customSectionDecoder(reader, size)
        SectionType.Type -> typeSectionDecoder(reader, size)
        SectionType.Import -> importSectionDecoder(reader, size)
        SectionType.Function -> functionSectionDecoder(reader, size)
        SectionType.Table -> tableSectionDecoder(reader, size)
        SectionType.Memory -> memorySectionDecoder(reader, size)
        SectionType.Global -> globalSectionDecoder(reader, size)
        SectionType.Export -> exportSectionDecoder(reader, size)
        SectionType.Start -> startSectionDecoder(reader, size)
        SectionType.Element -> elementSectionDecoder(reader, size)
        SectionType.Code -> codeSectionDecoder(reader, size)
        SectionType.Data -> dataSectionDecoder(reader, size)
        SectionType.DataCount -> dataCountSectionDecoder(reader, size)
    }
}
