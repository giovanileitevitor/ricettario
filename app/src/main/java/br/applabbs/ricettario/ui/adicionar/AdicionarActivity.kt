package br.applabbs.ricettario.ui.adicionar

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.aux.PermissionUtils
import br.applabbs.ricettario.aux.hideKeyboard
import br.applabbs.ricettario.aux.onDebouncedListener
import br.applabbs.ricettario.databinding.ActivityAdicionarBinding
import br.applabbs.ricettario.databinding.DialogAddStepBinding
import br.applabbs.ricettario.databinding.DialogAddedOkBinding
import br.applabbs.ricettario.domain.local.models.*
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel


class AdicionarActivity: AppCompatActivity() {

    private val viewModel : AdicionarViewModel by viewModel()
    private lateinit var binding: ActivityAdicionarBinding
    private lateinit var stepsAdapter: StepsAdapter
    private lateinit var fotosAdapter: FotosAdapter
    private val REQUEST_CODE = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPermissions()
        setupScreen()
        setupListeners()
        setupObservers()
    }

    override fun onBackPressed() {
        viewModel.deleteAllTemporarySteps()
        viewModel.deleteAllTermporayFotos()
        super.onBackPressed()
    }

    override fun onDestroy() {
        viewModel.deleteAllTemporarySteps()
        viewModel.deleteAllTermporayFotos()
        super.onDestroy()
    }

    private fun setupPermissions(){
        PermissionUtils.verifyPermissions(this, 1)
    }

    private fun setupScreen(){
        binding.edtTituloReceita.isFocusable = true
        binding.edtTituloReceita.text.clear()
    }

    private fun setupListeners(){
        binding.btnVoltar.onDebouncedListener {
            viewModel.deleteAllTemporarySteps()
            viewModel.deleteAllTermporayFotos()
            super.onBackPressed()
        }

        binding.btnCancelar.onDebouncedListener {
            viewModel.deleteAllTemporarySteps()
            viewModel.deleteAllTermporayFotos()
            super.onBackPressed()
        }

        binding.btnIncluirStep.onDebouncedListener{
            dialogAddStep()
        }

        binding.btnIncluirFoto.onDebouncedListener {
            dialogAddFoto()
        }

        binding.btnAdicionarReceita.onDebouncedListener {
            val tituloReceita = binding.edtTituloReceita.text.toString()
            if(tituloReceita.isNotBlank()){
                viewModel.addNovaReceita(tituloReceita = tituloReceita)
            }else{
                Toast.makeText(this, "Titulo da Receita não informado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers(){
        viewModel.getSteps.observe(this, Observer{ it ->
            setupStepsRv(it)
        })

        viewModel.getFotos.observe(this, Observer{  it ->
            setupFotosRv(it)
        })

        viewModel.error.observe(this, Observer{ errorState ->
            if(errorState.hasError){
                Toast.makeText(this, errorState.statusError, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.ack.observe(this, Observer{ ackState ->
            if(ackState.hasSuccess){
                showConfirmationDialog(msg = ackState.statusAck)
            }
        })

    }

    private fun setupStepsRv(steps: List<Step>){
        binding.rvSteps.setHasFixedSize(true)
        binding.rvSteps.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        stepsAdapter = StepsAdapter(steps, singleClickStep, longClickStep)
        binding.rvSteps.adapter = stepsAdapter
    }

    private val singleClickStep = { step: Step ->
        Toast.makeText(this, step.info, Toast.LENGTH_SHORT).show()
    }

    private val longClickStep = { step: Step ->
        viewModel.deleteStep(idStep = step.id ?: 0)
    }

    private fun setupFotosRv(fotos: List<Foto>){
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if((fotos.size % MAX_ITENS != 0) && (position == 0)){
                    MAX_ITENS
                }else{
                    MIN_ITENS
                }
            }
        }
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = spanSizeLookup
        binding.rvFotos.layoutManager = layoutManager
        fotosAdapter = FotosAdapter(fotos, Glide.with(this), singleClickFoto, longClickFoto)
        binding.rvFotos.adapter = fotosAdapter
    }

    private val singleClickFoto = { foto: Foto ->
        Toast.makeText(this, "Id: ${foto.imgAddress}", Toast.LENGTH_SHORT).show()
    }

    private val longClickFoto = { foto: Foto ->
        viewModel.deleteFoto(idFoto = foto.id ?: 0, imgAddress = foto.imgAddress )
    }

    private fun dialogAddStep(){
        val customDialog = AlertDialog.Builder(this).create()
        val bind : DialogAddStepBinding = DialogAddStepBinding.inflate(LayoutInflater.from(this))
        customDialog.apply {
            setView(bind.root)
            setCancelable(true)
        }.show()

        bind.btnAddStep.setOnClickListener {
            val stepText = bind.edtDetail.editableText.toString()
            if(stepText.isNotBlank()){
                hideKeyboard()
                viewModel.addNewStep(stepText = stepText)
                customDialog.dismiss()
            }else{
                hideKeyboard()
                customDialog.dismiss()
            }
        }

        bind.btnSkipStep.setOnClickListener {
            customDialog.dismiss()
        }

    }

    private fun dialogAddFoto(){
        if(!PermissionUtils.verifyPermissions(this, 999)){
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, REQUEST_CODE)
        } else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 999)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            val imageFile = data.extras?.get("data") as Bitmap
            viewModel.addNewFoto(imgAddress = viewModel.createImageFile(imageFile))
        }
    }

    private fun showConfirmationDialog(msg: String){
        val customDialog = AlertDialog.Builder(this).create()
        val bind: DialogAddedOkBinding = DialogAddedOkBinding.inflate(LayoutInflater.from(this))
        customDialog.apply {
            setView(bind.root)
            setCancelable(false)
        }.show()

        bind.txtTitleDialogStep.text = msg

        bind.btnOK.setOnClickListener {
            customDialog.dismiss()
            finish()
            super.onBackPressed()
        }
    }

    companion object{
        private const val MAX_ITENS = 2
        private const val MIN_ITENS = 1
    }
}

