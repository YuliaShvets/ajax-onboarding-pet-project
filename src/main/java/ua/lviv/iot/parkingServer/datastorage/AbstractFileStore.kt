package ua.lviv.iot.parkingServer.datastorage

import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import ua.lviv.iot.parkingServer.model.CsvData
import kotlin.math.max

abstract class AbstractFileStore<T : CsvData> {

    private fun readRecords(): List<T> {
        val fileName = "$RESULT_FOLDER/${getRecordName()}-${getDateTodayInString()}"
        return if (Files.exists(Paths.get(fileName))) readRecordsFrom(File(fileName)) else emptyList()
    }

    private fun readRecordsFrom(csv: File): List<T> {
        return csv.useLines { sequence ->
            sequence
                .drop(1)
                .map { lines -> lines.split(",") }
                .map { lineItems -> convert(lineItems) }
                .toList()
        }
    }

    private fun saveRecords(records: List<T>) {
        val file = File("$RESULT_FOLDER/${getRecordName()}-${getDateTodayInString()}.csv")
        OutputStreamWriter(FileOutputStream(file), StandardCharsets.UTF_8).use { writer ->
            writer.write(records[0].getHeaders() + "\n")
            for (record in records) {
                writer.write(record.toCSV() + "\n")
            }
        }
    }

    fun <T : CsvData> loadDataToHashmap(fileStore: AbstractFileStore<T>, map: MutableMap<Long, T>, id: AtomicLong) {
        map += fileStore.readRecords().associateBy { max(id.getAndIncrement(), it.id) }
    }

    fun <T : CsvData> saveDataToFile(fileStore: AbstractFileStore<T>, map: Map<Long, T>) {
        val dataList: List<T> = map.values.toList()
        fileStore.saveRecords(dataList)
    }

    protected abstract fun getRecordName(): String

    protected abstract fun convert(values: List<String>): T

    private fun getDateTodayInString(): String = SimpleDateFormat("yyyy-MM-dd")
        .format(Calendar.getInstance().time)

    companion object {
        const val RESULT_FOLDER = "src/main/resources"
    }
}
