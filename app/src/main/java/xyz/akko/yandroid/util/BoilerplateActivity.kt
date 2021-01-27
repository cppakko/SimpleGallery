package xyz.akko.yandroid.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity


open class BoilerplateActivity : AppCompatActivity() {
    protected fun hideKeyboard() {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    protected fun showKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    //TODO F
    /*protected fun shareDemo() {
        // shameless self promotion :dance:
        val shareDemo = ShareDemo(this)
        shareDemo.shareDemo()
    }*/
}