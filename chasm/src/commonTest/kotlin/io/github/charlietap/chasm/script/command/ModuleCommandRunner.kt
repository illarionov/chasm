package io.github.charlietap.chasm.script.command

import com.goncalossilva.resources.Resource
import io.github.charlietap.chasm.embedding.instance
import io.github.charlietap.chasm.embedding.module
import io.github.charlietap.chasm.flatMap
import io.github.charlietap.chasm.getOrNull
import io.github.charlietap.chasm.script.ScriptContext
import io.github.charlietap.sweet.lib.command.ModuleCommand

typealias ModuleCommandRunner = (ScriptContext, ModuleCommand) -> CommandResult

fun ModuleCommandRunner(
    context: ScriptContext,
    command: ModuleCommand,
): CommandResult {
    val moduleFilePath = context.binaryDirectory + "/" + command.filename
    val bytes = Resource(moduleFilePath).readBytes()

    val instance = module(bytes).flatMap {
        instance(context.store, it, emptyList())
    }.getOrNull() ?: return CommandResult.Failure(command, "Failed to instantiate module")

    context.instances[command.name] = instance

    return CommandResult.Success
}
