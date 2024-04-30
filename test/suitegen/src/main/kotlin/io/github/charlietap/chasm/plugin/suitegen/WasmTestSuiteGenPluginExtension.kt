package io.github.charlietap.chasm.plugin.suitegen

import javax.inject.Inject
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

open class WasmTestSuiteGenPluginExtension @Inject constructor(
    layout: ProjectLayout,
    objects: ObjectFactory,
) {
    val wabtVersion: Property<String> = objects.property(String::class.java)

    val testSuiteCommit: Property<String> = objects.property(String::class.java)

    val testSuiteDirectory: DirectoryProperty = objects.directoryProperty().convention(layout.buildDirectory.dir(DIR_TEST_SUITE))

    val testSuiteGenDirectory: DirectoryProperty = objects.directoryProperty().convention(layout.buildDirectory.dir(DIR_TEST_GEN))

    val proposals: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

    val excludes: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

    private companion object {
        const val DIR_TEST_SUITE = "wasm-testsuite"
        const val DIR_TEST_GEN = "wasm-testsuite-gen"
    }
}
