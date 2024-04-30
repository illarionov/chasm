package io.github.charlietap.chasm.plugin.suitegen.script

import io.github.charlietap.chasm.plugin.suitegen.script.command.Command

@JvmInline
value class Script(val commands: List<Command>)
