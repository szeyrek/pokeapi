package com.scz.globallogic.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scz.globallogic.component.LoaderViewState
import com.scz.globallogic.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

abstract class BaseViewModel : ViewModel() {

    private val _loaderViewState = MutableLiveData<Event<LoaderViewState>>()
    val loaderViewState: LiveData<Event<LoaderViewState>> = _loaderViewState

    private val _showErrorPopup = MutableLiveData<Event<String>>()
    val showErrorPopup: LiveData<Event<String>> = _showErrorPopup

    private val viewModelJob = SupervisorJob()

    protected val vmScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    protected open fun handleError(ex: Exception) {
        val message = ex.message ?: ex.toString()
        _showErrorPopup.postValue(Event(message))
    }

    suspend fun <T> Flow<T>.flowCall(
        showLoaderView: Boolean = true,
        exception: (Exception) -> Unit = ::handleError,
        call: (T) -> Unit
    ) {
        this.onStart {
            if (showLoaderView) {
                showLoaderView()
            }
        }.catch { err ->
            dismissLoaderView()
            exception.invoke(err as Exception)
        }.collect { variable: T ->
            dismissLoaderView()
            call.invoke(variable)
        }
    }

    private fun showLoaderView() {
        _loaderViewState.postValue(Event(LoaderViewState(true)))
    }

    private fun dismissLoaderView() {
        _loaderViewState.postValue(Event(LoaderViewState(false)))
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}