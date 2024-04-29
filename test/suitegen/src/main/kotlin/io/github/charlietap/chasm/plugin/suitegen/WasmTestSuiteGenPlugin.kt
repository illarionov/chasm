package io.github.charlietap.chasm.plugin.suitegen

import io.github.charlietap.chasm.plugin.suitegen.task.DownloadWabtTask
import io.github.charlietap.chasm.plugin.suitegen.task.SyncRepositoryTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

class WasmTestSuiteGenPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val extension = project.extensions
            .create<WasmTestSuiteGenPluginExtension>("chasm-suitegen-plugin-extension")

        project.tasks.register<SyncRepositoryTask>(
            TASK_NAME_SYNC_SUITE,
        ).configure {
            description = TASK_DESCRIPTION_SYNC_SUITE
            group = GROUP

            repositoryUrl.set(URL_TESTSUITE)
            outputDirectory.set(extension.testSuiteDirectory)
        }

        project.tasks.register<DownloadWabtTask>(
            TASK_NAME_DOWNLOAD_WABT,
        ).configure {
            description = TASK_DESCRIPTION_DOWNLOAD_WABT
            group = GROUP

            wabtVersion.set(extension.wabtVersion)
            outputDirectory.set(extension.wabtDirectory)
        }





    }

    private companion object {

        const val URL_TESTSUITE = "https://github.com/WebAssembly/testsuite.git"
        const val URL_WABT = "https://github.com/WebAssembly/wabt.git"

        const val GROUP = "suitegen"

        const val TASK_NAME_SYNC_SUITE = "syncWasmTestSuite"
        const val TASK_NAME_DOWNLOAD_WABT = "downloadWabt"

        const val TASK_DESCRIPTION_SYNC_SUITE = "Clones/Updates the wasm test suite"
        const val TASK_DESCRIPTION_DOWNLOAD_WABT = "Downloads the wabt cli"
    }
}
