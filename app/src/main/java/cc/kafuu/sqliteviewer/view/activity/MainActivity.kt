package cc.kafuu.sqliteviewer.view.activity

import android.content.Context
import android.content.Intent
import cc.kafuu.sqliteviewer.BR
import cc.kafuu.sqliteviewer.R
import cc.kafuu.sqliteviewer.common.core.CoreActivity
import cc.kafuu.sqliteviewer.databinding.ActivityMainBinding
import cc.kafuu.sqliteviewer.viewmodel.MainViewModel

class MainActivity : CoreActivity<ActivityMainBinding, MainViewModel>(
    MainViewModel::class.java,
    R.layout.activity_main,
    BR.viewModel
) {
    companion object {
        fun launchActivity(context: Context, sqlitePath: String) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("sqlite_path", sqlitePath)
            context.startActivity(intent)
        }
    }

    override fun init() {
        initViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.sqlite.value?.close()
    }

    private fun initViewModel() {
        mViewModel.sqlite.observe(this) { onOpenDatabase() }
        initDatabase()
    }

    private fun initDatabase() {

    }

    private fun onOpenDatabase() {

    }
}