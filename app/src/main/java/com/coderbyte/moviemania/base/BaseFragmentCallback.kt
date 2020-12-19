package com.coderbyte.moviemania.base

import android.content.Intent
import android.os.IBinder
import android.view.View
import androidx.annotation.AnimRes
import androidx.appcompat.widget.Toolbar
import com.coderbyte.moviemania.R

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
interface BaseFragmentCallback {

    fun showLoader()

    fun hideLoader()

    fun startActivity(
        intent: Intent,
        finishSelf: Boolean = false, @AnimRes enterAnim: Int = R.anim.slide_in_right, @AnimRes exitAnim: Int = R.anim.slide_out_left
    )

    fun startActivity(
        cls: Class<*>,
        finishSelf: Boolean = false, @AnimRes enterAnim: Int = R.anim.slide_in_right, @AnimRes exitAnim: Int = R.anim.slide_out_left
    )

    fun showToast(message: String?)

    fun showSnackBar(
        message: String?,
        parentLayout: View? = null,
        snackBarType: Int = SnackBarMessage.SNACK_BAR_DEFAULT
    )

    fun animateStartActivity(enterAnim: Int, exitAnim: Int)

    fun animateEndActivity(enterAnim: Int, exitAnim: Int)

    fun setupActionBar(toolbar: Toolbar, enableBackButton: Boolean)

    fun setupActionBarNull()

    fun hideKeyBoard(iBinder: IBinder)
}