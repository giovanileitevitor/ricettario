package br.applabbs.ricettario.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val _next : MutableLiveData<Boolean> = MutableLiveData()
    val next: LiveData<Boolean> = _next

    private val _isPermissionsAccepted = MutableLiveData<Boolean>()
    val isPermissionAccepted : LiveData<Boolean> = _isPermissionsAccepted

    fun getWaitProcess(){
        viewModelScope.launch {
            android.os.Handler().postDelayed({
                _next.postValue(true)
            }, 1000)
        }
    }

}