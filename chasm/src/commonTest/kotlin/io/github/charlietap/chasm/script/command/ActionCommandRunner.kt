package io.github.charlietap.chasm.script.command

import io.github.charlietap.chasm.script.ScriptContext
import io.github.charlietap.sweet.lib.command.ActionCommand

typealias ActionCommandRunner = (ScriptContext, ActionCommand) -> CommandResult

fun ActionCommandRunner(
    context: ScriptContext,
    command: ActionCommand,
): CommandResult {
    println("ignoring ActionCommand")
    return CommandResult.Success
}
