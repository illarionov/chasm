plugins {
    alias(libs.plugins.kotlin.multiplatform)

    alias(libs.plugins.conventions.kmp)
    alias(libs.plugins.conventions.linting)
}

kotlin {
    sourceSets {
       commonMain {
            dependencies {
                api(projects.executor.instantiator)
                api(projects.test.fixture.ast)
                api(projects.test.fixture.executor.runtime)
            }
        }
    }
}
