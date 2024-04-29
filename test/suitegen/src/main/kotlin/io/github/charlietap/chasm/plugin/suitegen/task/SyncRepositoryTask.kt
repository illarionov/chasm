package io.github.charlietap.chasm.plugin.suitegen.task

import java.nio.file.Files
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory

abstract class SyncRepositoryTask : Exec() {

    @get:Input
    abstract val repositoryUrl: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    override fun exec() {
        if (repositoryHasBeenCloned()) {
            workingDir = outputDirectory.get().asFile
            commandLine = listOf(
                "git", "pull",
            )
        } else {
            commandLine = listOf(
                "git", "clone", repositoryUrl.get(), outputDirectory.get().asFile.absolutePath,
            )
        }

        super.exec()
    }

    private fun repositoryHasBeenCloned(): Boolean {

        val outputDir = outputDirectory.get().asFile

        return if(outputDir.exists() && outputDir.isDirectory) {
            val path = outputDir.toPath()

            Files.list(path).findAny().isPresent
        } else false
    }
}
