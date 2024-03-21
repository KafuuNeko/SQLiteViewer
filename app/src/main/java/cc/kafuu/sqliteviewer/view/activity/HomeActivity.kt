package cc.kafuu.sqliteviewer.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import cc.kafuu.sqliteviewer.BR
import cc.kafuu.sqliteviewer.R
import cc.kafuu.sqliteviewer.common.adapter.DatabaseListAdapter
import cc.kafuu.sqliteviewer.common.core.CoreActivity
import cc.kafuu.sqliteviewer.common.utils.CommonLibs
import cc.kafuu.sqliteviewer.common.utils.FileUtils.getFileName
import cc.kafuu.sqliteviewer.databinding.ActivityHomeBinding
import cc.kafuu.sqliteviewer.viewmodel.HomeViewModel
import java.io.IOException


class HomeActivity : CoreActivity<ActivityHomeBinding, HomeViewModel>(
    HomeViewModel::class.java,
    R.layout.activity_home,
    BR.viewModel
) {
    private val mSelectorLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == Activity.RESULT_OK) {
                tryImportDB(result.data?.data)
            }
        }

    override fun init() {
        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        mViewModel.sqliteFiles.observe(this) {
            (mViewDataBinding.rvSqliteList.adapter as DatabaseListAdapter).apply {
                sqliteFiles = it
            }
            switchView(it.isEmpty())
            notifyDataSetChanged()
        }
        mViewModel.doLoadSqliteFiles()
    }

    private fun initViews() {
        mViewDataBinding.includeTitle.tvTitle.text = CommonLibs.getString(R.string.app_name)

        mViewDataBinding.btnCreateDb.setOnClickListener { tryCreateDB() }
        mViewDataBinding.btnImportDb.setOnClickListener { tryImportDB() }

        initRv()
    }

    private fun initRv() {
        mViewDataBinding.rvSqliteList.layoutManager = LinearLayoutManager(this)
        mViewDataBinding.rvSqliteList.adapter = DatabaseListAdapter()
    }

    private fun switchView(emptyView: Boolean) {
        mViewDataBinding.ivApp.visibility = if (emptyView) View.VISIBLE else View.GONE
        mViewDataBinding.rvSqliteList.visibility = if (emptyView) View.GONE else View.VISIBLE
        mViewDataBinding.includeTitle.root.visibility = if (emptyView) View.GONE else View.VISIBLE
    }

    private fun tryCreateDB() {

    }

    @SuppressLint("Recycle")
    private fun tryImportDB(dest: Uri? = null) {
        if (dest == null) {
            mSelectorLauncher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "application/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            })
            return
        }

        val fileName = dest.getFileName() ?: "unknown"

        try {
            contentResolver.openInputStream(dest)?.use { inputStream ->
                if (mViewModel.importSqlite(fileName, inputStream)) {
                    mViewModel.doLoadSqliteFiles()
                } else {
                    Toast.makeText(this, R.string.import_database_failed_tip, Toast.LENGTH_SHORT)
                        .show()
                }
            } ?: return
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyDataSetChanged() {
        mViewDataBinding.rvSqliteList.adapter?.notifyDataSetChanged()
    }

}