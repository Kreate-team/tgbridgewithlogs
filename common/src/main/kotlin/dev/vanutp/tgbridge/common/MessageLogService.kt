package dev.vanutp.tgbridge.common

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption.APPEND
import java.nio.file.StandardOpenOption.CREATE
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object MessageLogService {
    private var filePath: Path? = null
    private var logger: ILogger? = null

    fun init(logger: ILogger, configDir: Path) {
        this.logger = logger
        filePath = configDir.resolve("messages.log")
    }

    @Synchronized
    fun log(chatId: Long, text: String) {
        val path = filePath ?: return
        try {
            val ts = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            Files.writeString(path, "[$ts] [chat $chatId] $text\n", CREATE, APPEND)
        } catch (e: Exception) {
            logger?.warn("Failed to write messages.log: ${e.message}")
        }
    }
}
