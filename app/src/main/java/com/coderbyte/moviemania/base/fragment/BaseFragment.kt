package com.coderbyte.moviemania.base.fragment

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.coderbyte.moviemania.base.BaseFragmentCallback
import com.coderbyte.moviemania.base.viewmodel.BaseViewModel
import com.coderbyte.moviemania.data.session.Session
import com.coderbyte.moviemania.utils.AutoClearedValue
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.support.v4.toast
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
abstract class BaseFragment<ViewModel : BaseViewModel?, DataBinding : ViewDataBinding> : Fragment(),
    HasAndroidInjector {

    private var keyboardListenersAttached = false
    private var session: Session? = null



    @Inject
    lateinit var factory: ViewModelProvider.Factory
    var viewModel: ViewModel? = null
    var baseFragmentCallback: BaseFragmentCallback? = null
    var navController: NavController? = null

    private var dataBinding by AutoClearedValue<DataBinding>()

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>


    @get:LayoutRes
    abstract val layoutResourceId: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        if (context is BaseFragmentCallback) {
            this.baseFragmentCallback = context
        }
        viewModel = ViewModelProviders.of(this, factory).get(getViewModelClass())
        Handler().postDelayed({
            activity?.let {
                navController = findNavController()
            }
        }, 50)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return DataBindingUtil.inflate<DataBinding>(inflater, layoutResourceId, container, false)
            .also {
                dataBinding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        setVariables(dataBinding)

        viewModel?.showLoader?.observe(viewLifecycleOwner, Observer {
            if (it) baseFragmentCallback?.showLoader()
            else baseFragmentCallback?.hideLoader()
        })

        viewModel?.toastMessage?.observe(viewLifecycleOwner, Observer { msg ->
            msg?.let {
                toast(it)
                viewModel?.toastMessage?.postValue(null)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        session = null
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return fragmentInjector
    }

    fun setupActionBar(toolbar: Toolbar, enableBackButton: Boolean) {
        baseFragmentCallback?.setupActionBar(toolbar, enableBackButton)
    }

    fun navigateFragment(@IdRes navigationId: Int, bundle: Bundle? = null) {
        try {

        } catch (ex: Exception) {

        }
    }

    abstract fun setVariables(dataBinding: DataBinding)

    private fun getViewModelClass(): Class<ViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]   // index of 0 means first argument of Base class param
        return type as Class<ViewModel>
    }
}