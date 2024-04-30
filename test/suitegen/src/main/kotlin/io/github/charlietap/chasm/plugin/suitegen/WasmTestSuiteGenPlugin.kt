package io.github.charlietap.chasm.plugin.suitegen

import io.github.charlietap.chasm.plugin.suitegen.task.PrepareTestSuite
import io.github.charlietap.chasm.plugin.suitegen.task.ResolveWast2JsonTask
import io.github.charlietap.chasm.plugin.suitegen.task.SyncRepositoryTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

class WasmTestSuiteGenPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val extension = project.extensions
            .create<WasmTestSuiteGenPluginExtension>("chasm-suitegen-plugin-extension")

        val syncRepositoryTask = project.tasks.register<SyncRepositoryTask>(
            TASK_NAME_SYNC_SUITE,
        ) {
            description = TASK_DESCRIPTION_SYNC_SUITE
            group = GROUP

            repositoryUrl.set(URL_TESTSUITE)
            commitHash.set(extension.testSuiteCommit)
            outputDirectory.set(extension.testSuiteDirectory)
        }

        val resolveWast2JsonTask = project.tasks.register<ResolveWast2JsonTask>(
            TASK_NAME_RESOLVE_W2J,
        ) {
            description = TASK_DESCRIPTION_RESOLVE_W2J
            group = GROUP

            wabtVersion.set(extension.wabtVersion)
            outputFile.set(project.layout.buildDirectory.file("wast2json"))
        }

        project.tasks.register<PrepareTestSuite>(
            TASK_NAME_PREPARE_SUITE,
        ) {
            description = TASK_DESCRIPTION_PREPARE_SUITE
            group = GROUP

            inputFiles.apply {
                from(syncRepositoryTask.flatMap { it.outputDirectory })
                include("*.wast")
                extension.proposals.get().forEach { proposal ->
                    include("proposals/$proposal/*.wast")
                }
                exclude(extension.excludes.get())
                builtBy(syncRepositoryTask)
            }

            wast2Json.set(resolveWast2JsonTask.flatMap { it.outputFile })
            outputDirectory.set(extension.testSuiteGenDirectory)
        }





    }

    private companion object {

        const val URL_TESTSUITE = "https://github.com/WebAssembly/testsuite.git"

        const val GROUP = "suitegen"

        const val TASK_NAME_SYNC_SUITE = "syncWasmTestSuite"
        const val TASK_NAME_RESOLVE_W2J = "resolveWast2Json"
        const val TASK_NAME_PREPARE_SUITE = "prepareTestSuite"

        const val TASK_DESCRIPTION_SYNC_SUITE = "Clones/Updates the wasm test suite to the given commit"
        const val TASK_DESCRIPTION_RESOLVE_W2J = "Searches for wastjson on the local filesystem"
        const val TASK_DESCRIPTION_PREPARE_SUITE = "Prepare the wasm test suite for generation by running wast2json"
    }
}
