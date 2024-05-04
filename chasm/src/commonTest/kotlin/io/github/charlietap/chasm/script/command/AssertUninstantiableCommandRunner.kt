package io.github.charlietap.chasm.script.command

import io.github.charlietap.sweet.lib.command.AssertUninstantiableCommand

typealias AssertUninstantiableCommandRunner = (AssertUninstantiableCommand) -> CommandResult

fun AssertUninstantiableCommandRunner(
    command: AssertUninstantiableCommand,
): CommandResult {
    println("ignoring AssertUninstantiableCommand")
    return CommandResult.Success
}
