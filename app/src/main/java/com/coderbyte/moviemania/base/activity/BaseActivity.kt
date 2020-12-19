package com.coderbyte.moviemania.base.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.coderbyte.moviemania.R
import com.coderbyte.moviemania.base.BaseFragmentCallback
import com.coderbyte.moviemania.base.SnackBarMessage
import com.coderbyte.moviemania.base.viewmodel.BaseViewModel
import com.coderbyte.moviemania.ui.splash.SplashActivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import java.lang.Math.max
import java.lang.reflect.ParameterizedType
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.system.exitProcess

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
abstract class BaseActivity<ViewModel : BaseViewModel?, DataBinding : ViewDataBinding> :
    AppCompatActivity(), BaseFragmentCallback,
    HasAndroidInjector {

    private var internetDisposable: Disposable? = null
    private var connectivityDisposable: Disposable? = null

    private val loaderDialog: AlertDialog by lazy {

        val builder = MaterialAlertDialogBuilder(this@BaseActivity, R.style.LoaderDialog)
        val dialogView = LayoutInflater.from(applicationContext)
            .inflate(R.layout.dialog_loader, findViewById(android.R.id.content), false)
        builder.setView(dialogView)
        builder.setCancelable(false)
        return@lazy builder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    var dataBinding: DataBinding by Delegates.notNull()
    var viewModel: ViewModel? = null
    private var loaderRequest = 0

    @get:LayoutRes
    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
        dataBinding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, factory).get(getViewModelClass())
        setVariables(dataBinding)
        viewModel?.showLoader?.observe(this, Observer {
            if (it) showLoader()
            else hideLoader()
        })

        viewModel?.toastMessage?.observe(this, Observer {
            toast(it)
        })
    }

    override fun onResume() {
        super.onResume()

        connectivityDisposable = ReactiveNetwork.observeNetworkConnectivity(applicationContext)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity ->
                if (connectivity.state() == NetworkInfo.State.CONNECTED) {
                    connectivityStatus(true)
                } else {
                    connectivityStatus(false)
                }
            }

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToInternet ->
                internetConnectionStatus(isConnectedToInternet)
            }
    }

    override fun onPause() {
        super.onPause()
        safelyDispose(connectivityDisposable)
        safelyDispose(internetDisposable)
    }

    private fun safelyDispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return fragmentInjector
    }

    override fun showLoader() {
        try {
            runOnUiThread {
                if (!loaderDialog.isShowing) {
                    loaderDialog.show()
                    loaderRequest++
                }
            }
        } catch (ex: Exception) {

        }
    }

    override fun hideLoader() {
        try {
            runOnUiThread {
                loaderRequest = max(0, loaderRequest - 1)
                if (loaderDialog.isShowing && loaderRequest == 0)
                    loaderDialog.dismiss()
            }
        } catch (ex: Exception) {

        }
    }

    override fun startActivity(intent: Intent, finishSelf: Boolean, enterAnim: Int, exitAnim: Int) {
        try {
            startActivity(intent)
            animateStartActivity(enterAnim, exitAnim)
            if (finishSelf) {
                finish()
            }
        } catch (ex: Exception) {

        }

    }

    override fun startActivity(cls: Class<*>, finishSelf: Boolean, enterAnim: Int, exitAnim: Int) {
        try {
            startActivity(Intent(getContext(), cls), finishSelf, enterAnim, exitAnim)
        } catch (ex: Exception) {
            Log.e("error",ex.message ?: "Navigation error")
        }
    }

    override fun showToast(message: String?) {
        message?.let { runOnUiThread { toast(it) } }
    }

    override fun showSnackBar(message: String?, parentLayout: View?, snackBarType: Int) {
        message?.let { text ->
            runOnUiThread {
                val snackBar = Snackbar.make(
                    parentLayout ?: findViewById(android.R.id.content),
                    text,
                    Snackbar.LENGTH_LONG
                )
                if (snackBarType != SnackBarMessage.SNACK_BAR_DEFAULT) {
                    val view = snackBar.view
                    val textView =
                        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.setTextColor(getSnackBarColor(snackBarType))
                }
                snackBar.show()
            }
        }
    }

    override fun animateStartActivity(enterAnim: Int, exitAnim: Int) {
        overridePendingTransition(enterAnim, exitAnim)
    }

    override fun animateEndActivity(enterAnim: Int, exitAnim: Int) {
        overridePendingTransition(enterAnim, exitAnim)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return try {
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        } catch (ex: Exception) {
            false
        }
    }


    abstract fun setVariables(dataBinding: DataBinding)

    fun getContext(): Context = this

    override fun setupActionBar(toolbar: Toolbar, enableBackButton: Boolean) {
        setSupportActionBar(toolbar)
        if (enableBackButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left)
        }
    }

    override fun setupActionBarNull() {
        setSupportActionBar(null)
    }

    private fun getSnackBarColor(snackBarType: Int): Int {
        return when (snackBarType) {
            SnackBarMessage.SNACK_BAR_NORMAL -> ContextCompat.getColor(
                getContext(),
                android.R.color.white
            )
            SnackBarMessage.SNACK_BAR_SUCCESS -> ContextCompat.getColor(
                getContext(),
                android.R.color.white
            )
            SnackBarMessage.SNACK_BAR_ERROR -> ContextCompat.getColor(
                getContext(),
                android.R.color.white
            )
            else -> ContextCompat.getColor(getContext(), android.R.color.white)
        }
    }

    private fun getViewModelClass(): Class<ViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]   // index of 0 means first argument of Base class param
        return type as Class<ViewModel>
    }

    override fun hideKeyBoard(iBinder: IBinder) {
        try {
            val inputMethodManager =
                getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(iBinder, 0)
        } catch (ex: Exception) {

        }
    }

    open fun internetConnectionStatus(isConnectedToInternet: Boolean) {

    }

    open fun connectivityStatus(isConnected: Boolean) {

    }

    /**
     * only if android 8 -> api-26 and android 8.1 -> api-27
     */
    private fun doRestart(ctx: Context?) {
        try {
            //check if the context is given
            if (ctx != null) {
                //create the intent with the default start activity for your application
                val mStartActivity: Intent = Intent(ctx, SplashActivity::class.java)
                mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                //create a pending intent so the application is restarted after System.exit(0) was called.
                // We use an AlarmManager to call this intent in 100ms
                val mPendingIntentId = 223344
                val mPendingIntent: PendingIntent = PendingIntent
                    .getActivity(
                        ctx, mPendingIntentId, mStartActivity,
                        PendingIntent.FLAG_CANCEL_CURRENT
                    )
                val mgr: AlarmManager =
                    ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                mgr.set(
                    AlarmManager.RTC,
                    System.currentTimeMillis() + 1000,
                    mPendingIntent
                )
                //kill the application
                Looper.getMainLooper()?.let {
                    Handler(it).postDelayed({
                        exitProcess(0)
                    }, 200)
                }
            } else {
                Log.e("error", "Was not able to restart application, Context null")
            }
        } catch (ex: java.lang.Exception) {
            Log.e("error", "Was not able to restart application")
        }
    }
}