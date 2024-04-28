package io.github.charlietap.chasm.plugin.suitegen

import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

open class WasmTestSuiteGenPluginExtension @Inject constructor(
    objects: ObjectFactory,
) {
    val testSuiteDirectory: Property<String> = objects.property(String::class.java).convention(DIR_TEST_SUITE)

    val testSuiteGenDirectory: Property<String> = objects.property(String::class.java).convention(DIR_TEST_GEN)

    val wabtDirectory: Property<String> = objects.property(String::class.java).convention(DIR_WABT)

    val proposals: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())

    val excludes: ListProperty<String> = objects.listProperty(String::class.java).convention(emptyList())


    private companion object {
        const val DIR_TEST_SUITE = "wasm-testsuite"
        const val DIR_TEST_GEN = "wasm-testsuite-gen"
        const val DIR_WABT = "wasm-wabt"
    }
}
