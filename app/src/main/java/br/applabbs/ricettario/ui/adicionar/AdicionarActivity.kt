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
import br.applabbs.ricettario.aux.onDebouncedListener
import br.applabbs.ricettario.databinding.ActivityAdicionarBinding
import br.applabbs.ricettario.databinding.DialogAddStepBinding
import br.applabbs.ricettario.domain.local.models.*
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel


class AdicionarActivity: AppCompatActivity() {

    private val viewModel : AdicionarViewModel by viewModel()
    private lateinit var binding: ActivityAdicionarBinding
    private lateinit var rvStepsAdapter: RvStepsAdapter
    private lateinit var rvFotosAdapter: RvFotosAdapter
    private lateinit var receitaCompleta: ReceitaCompleta
    private val REQUEST_CODE = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupScreen()
        setupListeners()
        setupObservers()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setupScreen(){
        binding.edtTituloReceita.isFocusable = true
    }

    private fun setupListeners(){
        binding.btnVoltar.onDebouncedListener {
            super.onBackPressed()
        }

        binding.btnSalvar.onDebouncedListener {
            super.onBackPressed()
        }

        binding.btnApagar.onDebouncedListener {
            super.onBackPressed()
        }

        binding.btnIncluirStep.onDebouncedListener{
            dialogAddStep()
        }

        binding.btnIncluirFoto.onDebouncedListener {
            dialogAddFoto()
        }
    }

    private fun setupObservers(){
        viewModel.getSteps.observe(this, Observer{ it ->
            fillListSteps(it)
        })

        viewModel.getFotos.observe(this, Observer{  it ->
            fillListFotos(it)
        })

    }

    private fun fillListSteps(steps: List<Step>){
        binding.rvSteps.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvStepsAdapter = RvStepsAdapter(steps, singleClickStep)
        binding.rvSteps.adapter = rvStepsAdapter
    }

    private val singleClickStep = { step: Step ->
        Toast.makeText(this, step.info, Toast.LENGTH_SHORT).show()
    }

    private fun fillListFotos(fotos: List<Foto>){
        binding.rvFotos.setHasFixedSize(true)
        binding.rvFotos.layoutManager = GridLayoutManager(this, 2)
        rvFotosAdapter = RvFotosAdapter(fotos, Glide.with(this), singleClickFoto)
        binding.rvFotos.adapter = rvFotosAdapter
    }

    private val singleClickFoto = { foto: Foto ->
        Toast.makeText(this, "Id: ${foto.imgAddress}", Toast.LENGTH_SHORT).show()
    }

    private fun dialogAddStep(){
        val customDialog = AlertDialog.Builder(this).create()
        val bind : DialogAddStepBinding = DialogAddStepBinding.inflate(LayoutInflater.from(this))
        customDialog.apply {
            setView(bind.root)
            setCancelable(true)
        }.show()

        bind.edtDetail.focusable

        bind.btnAddStep.setOnClickListener {
            val stepText = bind.edtDetail.editableText.toString()
            if(stepText.isNotBlank()){
                viewModel.addNewStep(step = Step(info = stepText))
                customDialog.dismiss()
            }else{
                customDialog.dismiss()
            }
        }

        bind.btnSkipStep.setOnClickListener {
            customDialog.dismiss()
        }

    }

    private fun dialogAddFoto(){
        if(!PermissionUtils.verifyPermissions(this, 1)){
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
            viewModel.addNewFoto(Foto(imgAddress = viewModel.createImageFile(imageFile)))
        }
    }
}