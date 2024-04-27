package io.github.charlietap.chasm.plugin.suitegen.task

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory

abstract class SyncWasmTestSuiteTask : Exec() {

    @get:Input
    abstract val repositoryUrl: Property<String>

    @get:OutputDirectory
    abstract val testSuiteDirectory: DirectoryProperty

    override fun exec() {

        val testSuiteDir = testSuiteDirectory.get().asFile

        if (testSuiteDir.exists() && testSuiteDir.isDirectory) {
            workingDir = testSuiteDirectory.get().asFile
            commandLine = listOf(
                "git", "pull",
            )
        } else {
            commandLine = listOf(
                "git", "clone", repositoryUrl.get(), testSuiteDirectory.get().asFile.absolutePath,
            )
        }

        super.exec()
    }
}
