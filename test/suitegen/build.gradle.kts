plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    gradlePluginPortal()
}

kotlin {
    jvmToolchain {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(libs.versions.java.compiler.version.get().toInt()))
            vendor.set(JvmVendorSpec.matching(libs.versions.java.vendor.get()))
        }
    }
}

gradlePlugin {
    plugins {
        create("suitegen") {
            id = "chasm-suitegen-plugin"
            implementationClass = "io.github.charlietap.chasm.plugin.suitegen.WasmTestSuiteGenPlugin"
        }
    }
}
