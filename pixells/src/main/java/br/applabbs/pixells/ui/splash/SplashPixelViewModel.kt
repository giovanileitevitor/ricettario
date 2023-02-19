package br.applabbs.pixells.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SplashPixelViewModel: ViewModel() {

    private val _next : MutableLiveData<Boolean> = MutableLiveData()
    val next: LiveData<Boolean> = _next

    fun getWaitProcess(){
        viewModelScope.launch {
            android.os.Handler().postDelayed({
                _next.postValue(false)
            }, 3000)
        }
    }

}