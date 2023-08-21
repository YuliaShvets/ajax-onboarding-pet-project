package ua.lviv.iot.parkingServer.datastorage

import ua.lviv.iot.parkingServer.model.CsvData
import ua.lviv.iot.parkingServer.utils.Util
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.max

abstract class AbstractFileStore<T : CsvData> {
    private fun readRecords(): List<T> {
        val fileName = "$RESULT_FOLDER/${getRecordName()}-${Util.getDateTodayInString()}"
        return if (Files.exists(Paths.get(fileName))) readRecordsFrom(File(fileName)) else emptyList()
    }

    private fun readRecordsFrom(csv: File): List<T> {
        val result: MutableList<T> = LinkedList()
        val scanner = Scanner(csv, StandardCharsets.UTF_8)
        if (scanner.hasNextLine()) scanner.nextLine()
        while (scanner.hasNextLine()) {
            val values: List<String> = scanner.nextLine().split(", ").toList()
            result.add(convert(values))
        }
        return result
    }

    fun saveRecords(records: List<T>) {
        val file = File("$RESULT_FOLDER/${getRecordName()}-${Util.getDateTodayInString()}.csv")
        OutputStreamWriter(FileOutputStream(file), StandardCharsets.UTF_8).use { writer ->
            writer.write(records[0].getHeaders() + "\n")
            for (record in records) {
                writer.write(record.toCSV() + "\n")
            }
        }
    }

    fun <T : CsvData> loadDataToHashmap(fileStore: AbstractFileStore<T>, map: MutableMap<Long, T>, id: AtomicLong) {
        val data = fileStore.readRecords().associateBy { max(id.getAndIncrement(), it.id) }
        map.putAll(data)
    }

    fun <T : CsvData> saveDataToFile(fileStore: AbstractFileStore<T>, map: Map<Long, T>) {
        val dataList: List<T> = map.values.toList()
        fileStore.saveRecords(dataList)
    }

    protected abstract fun getRecordName(): String
    protected abstract fun convert(values: List<String>): T

    companion object {
        const val RESULT_FOLDER = "src/main/resources"
    }
}