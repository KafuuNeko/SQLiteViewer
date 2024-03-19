package cc.kafuu.sqliteviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import cc.kafuu.sqliteviewer.common.core.CoreViewModel
import cc.kafuu.sqliteviewer.common.utils.SQLiteUtils

class MainViewModel: CoreViewModel() {
    val sqlite = MutableLiveData<SQLiteUtils>()

}