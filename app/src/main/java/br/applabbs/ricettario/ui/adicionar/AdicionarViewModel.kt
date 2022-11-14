package br.applabbs.ricettario.ui.adicionar

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.applabbs.ricettario.domain.local.models.AckState
import br.applabbs.ricettario.domain.local.models.ErrorState
import br.applabbs.ricettario.domain.local.models.Foto
import br.applabbs.ricettario.domain.local.models.Step
import br.applabbs.ricettario.domain.local.usecases.ReceitaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AdicionarViewModel(
    private val context: Context,
    private val receitaUseCase: ReceitaUseCase
): ViewModel() {

    val getSteps: LiveData<List<Step>> get() = _getSteps
    private val _getSteps: MutableLiveData<List<Step>> = MutableLiveData()

    val getFotos: LiveData<List<Foto>> get() = _getFotos
    private val _getFotos: MutableLiveData<List<Foto>> = MutableLiveData()

    val error: LiveData<ErrorState> get() = _error
    private val _error: MutableLiveData<ErrorState> = MutableLiveData()

    val ack: LiveData<AckState> get() = _ack
    private val _ack: MutableLiveData<AckState> = MutableLiveData()


    fun addNewStep(stepText: String){
        viewModelScope.launch(Dispatchers.IO) {
            receitaUseCase.insertStepReceita(Step(info = stepText, isTemporary = true))
            updateScreen(type = UPDATE_STEPS)
        }
    }

    fun deleteStep(idStep: Int){
        viewModelScope.launch(Dispatchers.IO) {
            receitaUseCase.deleteStep(idStep = idStep)
            updateScreen(type = UPDATE_STEPS)
        }
    }

    fun deleteAllTemporarySteps(){
        viewModelScope.launch(Dispatchers.IO) {
            receitaUseCase.deleteAllTemporarySteps(isTemporary = true)
        }
    }

    fun addNewFoto(imgAddress: String){
        viewModelScope.launch(Dispatchers.IO) {
            receitaUseCase.insertFotoReceita(Foto(imgAddress = imgAddress, isTemporary = true))
            updateScreen(type = UPDATE_FOTOS)
        }
    }

    fun deleteFoto(idFoto: Int, imgAddress: String){
        viewModelScope.launch(Dispatchers.IO) {
            receitaUseCase.deleteFoto(idFoto = idFoto)
            deleteImage(path = imgAddress)
            updateScreen(type = UPDATE_FOTOS)
        }
    }

    fun deleteAllTermporayFotos(){
        viewModelScope.launch(Dispatchers.IO) {
            receitaUseCase.deleteAllTemporaryFotos(isTemporary = true)
        }
    }

    fun addNovaReceita(tituloReceita: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val steps = receitaUseCase.getSteps(isTemporary = true)
            val fotos = receitaUseCase.getFotos(isTemporary = true)

            if (steps.isNotEmpty() && fotos.isNotEmpty()) {
                val idReceita = receitaUseCase.insertReceita(
                    tituloReceita = tituloReceita,
                    steps = steps,
                    fotos = fotos
                )
                if(idReceita != 0L && idReceita != null){
                    updateObjects(idReceita = idReceita.toInt())
                    _ack.postValue(
                        AckState(
                            hasSuccess = true,
                            statusAck = "Receita Adicionada com sucesso \n:: ::\n${tituloReceita}"
                        )
                    )
                } else{
                    _error.postValue(
                        ErrorState(
                            hasError = true,
                            statusError = "Falha na gravação dos dados"
                        )
                    )
                }
            } else {
                _error.postValue(
                    ErrorState(
                        hasError = true,
                        statusError = "Verifique se as etapas e as fotos da receita foram adicionadas."
                    )
                )
            }
        }
    }

    private fun updateScreen(type: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(type){
                UPDATE_STEPS -> _getSteps.postValue(receitaUseCase.getSteps(isTemporary = true))
                UPDATE_FOTOS ->  _getFotos.postValue(receitaUseCase.getFotos(isTemporary = true))
            }
        }
    }

    private fun updateObjects(idReceita : Int){
        viewModelScope.launch(Dispatchers.IO) {
            receitaUseCase.updateSteps(idReceita = idReceita)
            receitaUseCase.updateFotos(idReceita = idReceita)
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

    private fun deleteImage(path: String) {
        val fDelete = File(path)
        if (fDelete.exists()) {
            if (fDelete.delete()) {
                MediaScannerConnection.scanFile(context, arrayOf(Environment.getExternalStorageDirectory().toString()), null) { path, uri ->
                    Log.d("debug", "DONE")
                }
            }
        }
    }

    companion object{
        private const val UPDATE_STEPS = 1
        private const val UPDATE_FOTOS = 2
    }

}