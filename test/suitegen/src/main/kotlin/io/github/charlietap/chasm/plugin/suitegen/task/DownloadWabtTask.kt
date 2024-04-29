package io.github.charlietap.chasm.plugin.suitegen.task

import java.io.File
import java.net.URI
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.ArchiveOperations
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem

abstract class DownloadWabtTask : DefaultTask() {

    @get:Input
    abstract val wabtVersion: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @get:Inject
    abstract val archive: ArchiveOperations

    @get:Inject
    abstract val fs: FileSystemOperations

    @Internal
    val temporaryFile: RegularFileProperty = project.layout.buildDirectory.file("")


    @TaskAction
    fun download() {

        if (!OperatingSystem.current().isMacOsX) {
            throw GradleException("DownloadWabtTask is only supported on macOS.")
        }

        val compressedFile = File(temporaryDir, TEMP_FILE_NAME)

        if (!compressedFile.exists()) {

            val url = RELEASE_TEMPLATE_MAC.replace("%s", wabtVersion.get())
            logger.info("Downloading WABT from $url")

            val website = URI(url).toURL()
            val connection = website.openConnection()
            connection.connect()

            compressedFile.outputStream().use { fileOutputStream ->
                connection.getInputStream().use { inputStream ->
                    fileOutputStream.write(inputStream.readBytes())
                }
            }

            val binaries = archive.tarTree(compressedFile).matching {
                include("*/bin/*")
            }

            fs.copy {
                from(binaries)
                into(outputDirectory)
            }

            fs.delete {
                delete(compressedFile)
            }
        }
    }

    private companion object {
        // Hardcode for mac, wabt will eventually run on chasm using WASI
        const val RELEASE_TEMPLATE_MAC = "https://github.com/WebAssembly/wabt/releases/download/%s/wabt-%s-macos-12.tar.gz"
        const val TEMP_FILE_NAME = "wabt.tar.gz"
    }
}
