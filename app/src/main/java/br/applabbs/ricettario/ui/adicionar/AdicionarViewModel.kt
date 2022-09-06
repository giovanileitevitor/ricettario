package br.applabbs.ricettario.ui.adicionar

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.applabbs.ricettario.domain.local.models.*
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class AdicionarViewModel(
    private val context: Context,
    private val receitaUseCase: ReceitaUseCase
): ViewModel() {

    val getSteps: LiveData<List<Step>> get() = _getSteps
    private val _getSteps: MutableLiveData<List<Step>> = MutableLiveData()
    private var steps: ArrayList<Step> = ArrayList<Step>()

    val getFotos: LiveData<List<Foto>> get() = _getFotos
    private val _getFotos: MutableLiveData<List<Foto>> = MutableLiveData()
    private lateinit var fotos : List<Foto>

    fun addNovaReceita(){
        viewModelScope.launch {
            //TODO - faz a gravação no DB de uma nova receita
        }
    }

    fun addNewStep(step: Step){
        viewModelScope.launch {
            receitaUseCase.insertStepReceita(step = step)
            updateScreen()
        }
    }

    fun addNewFoto(foto: Foto){
        viewModelScope.launch {
           receitaUseCase.insertFotoReceita(foto = foto)
            updateScreen()
        }
    }

    fun updateScreen(){
        viewModelScope.launch {
            _getSteps.postValue(receitaUseCase.getSteps(idReceita = 1))
            _getFotos.postValue(receitaUseCase.getFotos(idReceita = 1))
        }
    }

    fun createImageFile(imageId: Bitmap): String {
        val wrapper = ContextWrapper(context)
        val file = wrapper.getExternalFilesDir("/")?.absolutePath.toString()
        val arquive = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream : OutputStream = FileOutputStream(arquive)
            imageId.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException){ e.printStackTrace() }
        return arquive.absolutePath
    }


}