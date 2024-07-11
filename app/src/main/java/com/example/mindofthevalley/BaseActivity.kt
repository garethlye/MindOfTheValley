package com.example.mindofthevalley

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mindofthevalley.databinding.LayoutBaseBinding

abstract class BaseActivity: AppCompatActivity() {

    abstract fun bindViewModel()
    abstract val layoutResource: Int

    private var mViewModel: BaseViewModel? = null

    protected open fun <T : ViewDataBinding?> initBinding(viewModel: BaseViewModelImpl): T {
        val rootView = layoutInflater.inflate(
            R.layout.layout_base,
            window.decorView as ViewGroup,
            false
        ) as DrawerLayout
        val baseBinding = DataBindingUtil.bind<LayoutBaseBinding>(rootView)
        val binding: T = DataBindingUtil.inflate<T>(
            layoutInflater,
            layoutResource,
            rootView.findViewById(R.id.viewStub),
            true
        )
        binding!!.setVariable(BR.viewModel, viewModel)
        setContentView(rootView)
        baseBinding!!.viewModel = viewModel
        mViewModel = viewModel
        return binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        setupView()
    }

    open fun setupView() {

    }
}