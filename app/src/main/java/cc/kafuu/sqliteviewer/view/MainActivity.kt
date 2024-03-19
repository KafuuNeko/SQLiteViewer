package cc.kafuu.sqliteviewer.view

import android.util.Log
import android.widget.Toast
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
        private const val TAG = "MainActivity"
    }

    override fun initViews() {

    }
}