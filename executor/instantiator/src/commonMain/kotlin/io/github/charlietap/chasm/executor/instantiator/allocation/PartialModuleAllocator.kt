package io.github.charlietap.chasm.executor.instantiator.allocation

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import com.github.michaelbull.result.mapError
import io.github.charlietap.chasm.ast.module.Function
import io.github.charlietap.chasm.ast.module.Type
import io.github.charlietap.chasm.executor.instantiator.allocation.function.WasmFunctionAllocator
import io.github.charlietap.chasm.executor.instantiator.context.InstantiationContext
import io.github.charlietap.chasm.executor.instantiator.matching.ImportMatcher
import io.github.charlietap.chasm.executor.instantiator.predecoding.FunctionPredecoder
import io.github.charlietap.chasm.executor.instantiator.predecoding.Predecoder
import io.github.charlietap.chasm.executor.runtime.error.InstantiationError
import io.github.charlietap.chasm.executor.runtime.error.ModuleTrapError
import io.github.charlietap.chasm.executor.runtime.ext.addFunctionAddress
import io.github.charlietap.chasm.executor.runtime.ext.addGlobalAddress
import io.github.charlietap.chasm.executor.runtime.ext.addMemoryAddress
import io.github.charlietap.chasm.executor.runtime.ext.addTableAddress
import io.github.charlietap.chasm.executor.runtime.ext.addTagAddress
import io.github.charlietap.chasm.executor.runtime.ext.function
import io.github.charlietap.chasm.executor.runtime.ext.functionAddress
import io.github.charlietap.chasm.executor.runtime.instance.ExternalValue
import io.github.charlietap.chasm.executor.runtime.instance.FunctionInstance
import io.github.charlietap.chasm.executor.runtime.instance.Import
import io.github.charlietap.chasm.executor.runtime.instance.ModuleInstance
import io.github.charlietap.chasm.type.factory.DefinedTypeFactory
import io.github.charlietap.chasm.executor.runtime.function.Function as RuntimeFunction

internal typealias PartialModuleAllocator = (InstantiationContext, List<Import>) -> Result<ModuleInstance, ModuleTrapError>

internal fun PartialModuleAllocator(
    context: InstantiationContext,
    imports: List<Import>,
): Result<ModuleInstance, ModuleTrapError> =
    PartialModuleAllocator(
        context = context,
        imports = imports,
        wasmFunctionAllocator = ::WasmFunctionAllocator,
        typeAllocator = ::DefinedTypeFactory,
        importMatcher = ::ImportMatcher,
        functionPredecoder = ::FunctionPredecoder,
    )

internal inline fun PartialModuleAllocator(
    context: InstantiationContext,
    imports: List<Import>,
    crossinline wasmFunctionAllocator: WasmFunctionAllocator,
    crossinline typeAllocator: DefinedTypeFactory,
    crossinline importMatcher: ImportMatcher,
    crossinline functionPredecoder: Predecoder<Function, RuntimeFunction>,
): Result<ModuleInstance, ModuleTrapError> = binding {

    val module = context.module

    val instance = ModuleInstance(typeAllocator(module.types.map(Type::recursiveType)))
    context.instance = instance

    val matchedImports = importMatcher(context, imports)
        .mapError {
            InstantiationError.MissingImport
        }.bind()

    matchedImports.forEach { import ->
        when (import) {
            is ExternalValue.Function -> instance.addFunctionAddress(import.address)
            is ExternalValue.Table -> instance.addTableAddress(import.address)
            is ExternalValue.Memory -> instance.addMemoryAddress(import.address)
            is ExternalValue.Global -> instance.addGlobalAddress(import.address)
            is ExternalValue.Tag -> instance.addTagAddress(import.address)
        }
    }

    module.functions.forEach { function ->
        wasmFunctionAllocator(context, instance, function).bind()
    }

    module.functions.forEach { function ->

        val predecoded = functionPredecoder(context, function).bind()
        val address = instance.functionAddress(function.idx).bind()
        val functionInstance = context.store.function(address).bind() as FunctionInstance.WasmFunction

        functionInstance.function = predecoded
    }

    instance
}
