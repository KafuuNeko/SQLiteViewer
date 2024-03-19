package cc.kafuu.sqliteviewer.view.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import cc.kafuu.sqliteviewer.BR
import cc.kafuu.sqliteviewer.R
import cc.kafuu.sqliteviewer.common.core.CoreActivity
import cc.kafuu.sqliteviewer.common.utils.CommonLibs
import cc.kafuu.sqliteviewer.common.utils.PermissionUtils
import cc.kafuu.sqliteviewer.databinding.ActivityHomeBinding
import cc.kafuu.sqliteviewer.viewmodel.HomeViewModel

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
    }

    private fun initViews() {
        mViewDataBinding.includeTitle.tvTitle.text = CommonLibs.getString(R.string.app_name)


        mViewDataBinding.btnCreateDb.setOnClickListener {
            //tryCreateDB()
            switchView(true)
        }
        mViewDataBinding.btnImportDb.setOnClickListener {
            //tryImportDB()
            switchView(false)
        }
    }

    private fun switchView(emptyView: Boolean) {
        mViewDataBinding.ivApp.visibility = if (emptyView) View.VISIBLE else View.GONE
        mViewDataBinding.rvSqliteList.visibility = if (emptyView) View.GONE else View.VISIBLE
        mViewDataBinding.includeTitle.root.visibility = if (emptyView) View.GONE else View.VISIBLE
    }

    private fun tryCreateDB() {

    }

    private fun tryImportDB(dest: Uri? = null) {
        if (dest == null) {
            mSelectorLauncher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "application/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            })
            return
        }
        dest.path?.let { MainActivity.launchActivity(this, it) }
    }
}