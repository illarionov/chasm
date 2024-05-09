package io.github.charlietap.chasm.script.command

import io.github.charlietap.sweet.lib.command.AssertTrapCommand

typealias AssertTrapCommandRunner = (AssertTrapCommand) -> CommandResult

fun AssertTrapCommandRunner(
    command: AssertTrapCommand,
): CommandResult {
    println("ignoring AssertTrapCommand")
    return CommandResult.Success
}
