package io.github.charlietap.chasm.plugin.suitegen

import io.github.charlietap.chasm.plugin.suitegen.task.SyncWasmTestSuiteTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

class WasmTestSuiteGenPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val extension = project.extensions
            .create<WasmTestSuiteGenPluginExtension>("chasm-suitegen-plugin-extension")

        project.tasks.register<SyncWasmTestSuiteTask>(
            TASK_NAME_SYNC_SUITE,
        ).configure {
            description = TASK_DESCRIPTION_SYNC_SUITE
            group = GROUP

            repositoryUrl.set(URL_TESTSUITE)
            testSuiteDirectory.set(project.layout.buildDirectory.dir(extension.testSuiteDirectory))
        }


    }

    private companion object {

        const val URL_TESTSUITE = "https://github.com/WebAssembly/testsuite.git"

        const val GROUP = "suitegen"

        const val TASK_NAME_SYNC_SUITE = "syncWasmTestSuite"

        const val TASK_DESCRIPTION_SYNC_SUITE = "Clones/Updates the wasm test suite"
    }
}
