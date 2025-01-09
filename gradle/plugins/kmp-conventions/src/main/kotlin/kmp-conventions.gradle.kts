
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

val libs = the<LibrariesForLibs>()

fun KotlinMultiplatformExtension.unixTargets() = setOf(
    macosArm64 {
        binaries {
            test(listOf(RELEASE)) {

            }
        }
    },
    macosX64(),
    iosArm64(),
    iosSimulatorArm64(),
    iosX64(),
    linuxArm64(),
    linuxX64(),
)

kotlin {

    compilerOptions {
        extraWarnings.set(true)

        freeCompilerArgs.add("-opt-in=kotlin.ExperimentalUnsignedTypes")

        freeCompilerArgs.add("-Xsuppress-warning=NOTHING_TO_INLINE")
        freeCompilerArgs.add("-Xsuppress-warning=UNUSED_ANONYMOUS_PARAMETER")
        freeCompilerArgs.add("-Xsuppress-warning=REDUNDANT_VISIBILITY_MODIFIER")

        freeCompilerArgs.add("-Xnon-local-break-continue")
        freeCompilerArgs.add("-Xwhen-guards")
    }

    jvm {
        compilerOptions {
            freeCompilerArgs.add("-Xno-call-assertions")
            freeCompilerArgs.add("-Xno-param-assertions")
            freeCompilerArgs.add("-Xno-receiver-assertions")
        }
    }
    unixTargets()

    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.compiler.version.get().toInt()))
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(libs.versions.java.bytecode.version.get())
    }
}
