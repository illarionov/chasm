
import io.github.charlietap.sweet.plugin.WasmTestSuiteGenPluginExtension
import io.github.charlietap.sweet.plugin.task.GenerateTestsTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.atomic.fu)
    alias(libs.plugins.kotlinx.test.resources)
    alias(libs.plugins.wasm.testsuite)

    alias(libs.plugins.conventions.kmp)
    alias(libs.plugins.conventions.linting)
    alias(libs.plugins.conventions.publishing)
}

kotlin {

    sourceSets {

        all {
            languageSettings {
                optIn("kotlin.ExperimentalUnsignedTypes")
            }
        }

       commonMain {
            dependencies {
                implementation(projects.ast)

                implementation(projects.decoder)

                implementation(projects.executor.instantiator)
                implementation(projects.executor.invoker)
                implementation(projects.executor.runtime)
                implementation(projects.executor.memory)

                implementation(projects.typeSystem)

                implementation(projects.validator)

                implementation(libs.result)
            }
        }

        commonTest {
            dependencies {
                implementation(projects.test.fake.decoder)
                implementation(projects.test.fixture.ast)
                implementation(projects.test.fixture.executor.runtime)

                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.test.resources)
                implementation(libs.sweet.lib)
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.io.core)
            }
        }
    }
}

configure<PublishingConventionsExtension> {
    name = "chasm"
    description = "A wasm runtime for Kotlin Multiplatform"
}

configure<WasmTestSuiteGenPluginExtension> {
    wabtVersion = "1.0.36"
    testSuiteCommit = "7c3ec23ab19b37c68976b555f9491752cbda6d5f"
    scriptRunner = "io.github.charlietap.chasm.script.ChasmScriptRunner"
    testPackageName = "io.github.charlietap.chasm"
    proposals = listOf("tail-call", "extended-const", "multi-memory")
    excludes = listOf("simd_*/**", "**/simd_*", "align.wast", "binary.wast", "global.wast", "imports.wast", "memory.wast")
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(libs.versions.java.bytecode.version.get())
    }
}

tasks.named {
    it.contains("lintKotlinCommonTest")
}.configureEach {
    dependsOn(tasks.withType<GenerateTestsTask>())
}
