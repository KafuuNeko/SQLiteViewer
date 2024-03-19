package cc.kafuu.sqliteviewer.common.core

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider


abstract class CoreActivity<V : ViewDataBinding, VM : CoreViewModel> (
    private val vmClass: Class<VM>,
    private val layoutId: Int,
    private val viewModelId: Int
) : AppCompatActivity() {
    protected lateinit var mViewDataBinding: V
    protected lateinit var mViewModel: VM

    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        mViewModel = ViewModelProvider(this)[vmClass]
        if (viewModelId != 0) {
            mViewDataBinding.setVariable(viewModelId, mViewModel)
        }
        mViewDataBinding.lifecycleOwner = this
        initViews()
    }
}