package com.coderbyte.moviemania.base.viewmodel

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import com.coderbyte.moviemania.base.SnackBarMessage
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan on 19/12/20
 * Brain Station 23
 */
open class BaseViewModel @Inject constructor() : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }
    val showLoader by lazy { SingleLiveEvent<Boolean>() }
    val snackBarMessage by lazy { SingleLiveEvent<SnackBarMessage>() }
    val toastMessage by lazy { SingleLiveEvent<String>() }

    val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        showLoader.value = false
        toastMessage.postValue(null)
        compositeDisposable.dispose()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    @Suppress("unused")
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}