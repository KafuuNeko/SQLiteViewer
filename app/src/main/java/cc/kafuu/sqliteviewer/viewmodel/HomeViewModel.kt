package cc.kafuu.sqliteviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.kafuu.sqliteviewer.common.core.CoreViewModel
import cc.kafuu.sqliteviewer.common.model.SQLiteFile
import cc.kafuu.sqliteviewer.common.utils.CommonLibs
import cc.kafuu.sqliteviewer.common.utils.SQLiteUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class HomeViewModel : CoreViewModel() {
    val sqliteFiles: MutableLiveData<List<SQLiteFile>> = MutableLiveData()

    fun doLoadSqliteFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            val files = CommonLibs.sqliteDir.listFiles()
            val newSqliteFiles = files?.mapNotNull { file ->
                readSqliteFile(file)
            }.orEmpty()
            sqliteFiles.postValue(newSqliteFiles)
        }
    }

    private fun readSqliteFile(file: File): SQLiteFile = SQLiteUtils(file).use { sqliteUtils ->
        SQLiteFile(
            file.name,
            file,
            sqliteUtils.getEntityCount(SQLiteUtils.ENTITY_TABLE),
            sqliteUtils.getEntityCount(SQLiteUtils.ENTITY_VIEW)
        )
    }

    fun importSqlite(name: String, istream: InputStream): Boolean {
        val targetFile = File(CommonLibs.sqliteDir, "$name.db")
        if (!targetFile.exists()) {
            targetFile.createNewFile()
        }
        try {
            FileOutputStream(targetFile).use { output ->
                istream.copyTo(output)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}