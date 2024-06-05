package com.example.mindofthevalley

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mindofthevalley.data.Category
import com.example.mindofthevalley.data.Channel
import com.example.mindofthevalley.data.Course
import com.example.mindofthevalley.data.Data
import com.example.mindofthevalley.databinding.LayoutBaseBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.nio.channels.Channels

abstract class BaseActivity: AppCompatActivity() {

    abstract fun bindViewModel()
    abstract val layoutResource: Int

    private var mViewModel: BaseViewModel? = null
    private val gson = Gson()

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

    fun saveJsonToSharedPreferences(jsonResponse: Data, prefName: String, key: String) {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, gson.toJson(jsonResponse))
        editor.apply()
    }

    private inline fun <reified T> fromJson(json: String): T = Gson().fromJson(json, object: TypeToken<T>() {}.type)

    fun getJsonFromSharedPreferences(prefName: String, key: String): Data? {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(key, null)
        return json?.let { fromJson<Data>(it) }
    }

    enum class DataType {
        Category, Episodes, Channels, All
    }
}