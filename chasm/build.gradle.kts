import io.github.charlietap.chasm.plugin.suitegen.WasmTestSuiteGenPluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.atomic.fu)
    alias(libs.plugins.kotlinx.test.resources)
    id("kmp-conventions")
    id("linting-conventions")
    id("publishing-conventions")
    id("chasm-suitegen-plugin")
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
                api(projects.ast)
                api(projects.executor.runtime)

                implementation(projects.decoder.wasm)
                implementation(projects.decoder.wat)

                implementation(projects.executor.instantiator)
                implementation(projects.executor.invoker)
                implementation(projects.executor.runtimeExt)

                implementation(projects.executor.memory)

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
            }
        }
    }
}

configure<PublishingConventionsExtension> {
    name = "chasm"
    description = "A wasm runtime for Kotlin Multiplatform"
}

configure<WasmTestSuiteGenPluginExtension> {
    wabtVersion = "1.0.34"
    testSuiteCommit = "16a839d5601c283541a84572b47637f035b51437"
    proposals = listOf("tail-call", "extended-const")
    excludes = listOf("**/comments.wast","**/if.wast")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = libs.versions.java.bytecode.version.get()
}
