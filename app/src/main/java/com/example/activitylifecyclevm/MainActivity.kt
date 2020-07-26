package com.example.activitylifecyclevm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var strLog: String? = ""

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, SavedStateViewModelFactory(application, this)).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onClickClearLog()

        if (savedInstanceState !== null) {
            callbackState(viewModel.getLog().toString())
            callbackState(getString(R.string.txt_state_recreate))
        }

        callbackState(getString(R.string.txt_state_create))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        callbackState(getString(R.string.txt_state_save))
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        callbackState(getString(R.string.txt_state_restore))
    }

    override fun onStart() {
        super.onStart()
        callbackState(getString(R.string.txt_state_start))
    }

    override fun onResume() {
        super.onResume()
        callbackState(getString(R.string.txt_state_resume))
    }

    override fun onPause() {
        super.onPause()
        callbackState(getString(R.string.txt_state_pause))
    }

    override fun onStop() {
        super.onStop()
        callbackState(getString(R.string.txt_state_stop))
    }

    override fun onDestroy() {
        super.onDestroy()
        callbackState(getString(R.string.txt_state_destory))
    }

    //端末の戻る
    override fun onBackPressed() {}

    private fun onClickClearLog() {
        button1.setOnClickListener {
            log_view.text = ""
            strLog = ""
            viewModel.removeState()
        }
    }

    private fun callbackState(str: String) {

        //strLog = strLog + str + "\n" + viewModel.hashCode()
        strLog = strLog + str + "\n"
        log_view.text = strLog
        viewModel.saveLog(strLog!!)
        scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }
    }
}

class MainViewModel(private val savedState: SavedStateHandle) : ViewModel()  {
/*
    companion object {
        private const val UP_LOG = ""
    }

 */

    fun saveLog(upLog: String) {
        savedState.set("UP_LOG", upLog)
    }

    fun removeState() {
        savedState.remove<String>("UP_LOG")
    }

    fun getLog(): String? {
        return savedState.get<String>("UP_LOG")
    }
}

