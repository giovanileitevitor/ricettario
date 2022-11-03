package br.applabbs.ricettario.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.aux.emptyReceita
import br.applabbs.ricettario.aux.onDebouncedListener
import br.applabbs.ricettario.databinding.ActivityHomeBinding
import br.applabbs.ricettario.databinding.DialogActionsReceitaBinding
import br.applabbs.ricettario.databinding.DialogSearchReceitaBinding
import br.applabbs.ricettario.domain.local.models.Receita
import br.applabbs.ricettario.domain.local.models.Step
import br.applabbs.ricettario.ui.adicionar.AdicionarActivity
import br.applabbs.ricettario.ui.configurar.ConfigurarActivity
import br.applabbs.ricettario.ui.exibir.ExibirActivity
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel : HomeViewModel by viewModel()
    private var idReceita: Int = 0
    private lateinit var binding: ActivityHomeBinding
    private lateinit var receitasAdapter: ReceitasAdapter
    private lateinit var detalhesReceitaAdapter: DetalhesReceitaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.getAllReceitas()
    }

    private fun setupView(){
        viewModel.getAllReceitas()
    }

    private fun setupListeners(){
        binding.btnAdd.onDebouncedListener {
            startActivity(Intent(this, AdicionarActivity::class.java))
        }

        binding.btnSearch.onDebouncedListener {
            showDialogSearch()
        }

        binding.btnConfig.onDebouncedListener {
            startActivity(Intent(this, ConfigurarActivity::class.java))
        }

        binding.btnFotos.onDebouncedListener{
            openFotosScreen(idReceita = idReceita)
        }

    }

    private fun setupObservers(){
        viewModel.getReceitas.observe(this, Observer { it ->
            if(it.isNotEmpty()){
                setupAdapterReceitas(it)
                binding.emptyReceitaContainer.visibility = GONE
            }else{
                binding.emptyReceitaContainer.visibility = VISIBLE
                binding.rvReceitas.visibility = GONE
                binding.txtQtdReceitas.visibility = GONE
            }
        })

        viewModel.getStepsReceita.observe(this, Observer { it ->
            setupAdapterDetalhesReceita(steps = it)
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.loadingBox.root.isVisible = !isLoading
        })

        viewModel.isError.observe(this, Observer {
            showToast("Error")
        })
    }

    private fun setupAdapterReceitas(receitas : List<Receita>){
        binding.rvReceitas.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        receitasAdapter = ReceitasAdapter(receitas, Glide.with(this), singleClick, longClick)
        binding.rvReceitas.adapter = receitasAdapter
        binding.rvReceitas.visibility = VISIBLE
        binding.containerDetalhesReceita.visibility = INVISIBLE
        showQtdReceitas(qtdReceitas = receitas.size)
    }

    private val singleClick= { receita: Receita ->
        idReceita = receita.id
        binding.containerDetalhesReceita.visibility = VISIBLE
        binding.txtTitleReceita.text = receita.titulo
        viewModel.getStepsReceita(idReceita = idReceita)
    }

    private val longClick = { receita: Receita ->
        showOptionsDialog(receita = receita)
    }

    private fun showQtdReceitas(qtdReceitas : Int){
        val auxText : String = if(qtdReceitas == 1){ "receita disponível"} else{ "receitas disponíveis"}
        binding.txtQtdReceitas.text = "$qtdReceitas $auxText"
        binding.txtQtdReceitas.visibility = VISIBLE
    }

    private fun setupAdapterDetalhesReceita(steps: List<Step>){
        binding.rvDetalhesReceita.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        detalhesReceitaAdapter = DetalhesReceitaAdapter(steps, singleClickStep)
        binding.rvDetalhesReceita.adapter = detalhesReceitaAdapter
    }

    private val singleClickStep = { step: Step ->
        showToast("${step.info}")
    }

    private fun showDialogSearch(){
        val customDialog = AlertDialog.Builder(this).create()
        val bind : DialogSearchReceitaBinding = DialogSearchReceitaBinding.inflate(LayoutInflater.from(this))
        customDialog.apply {
            setView(bind.root)
            setCancelable(true)
        }.show()

        bind.btnSearchReceita.onDebouncedListener{
            val titleReceita = bind.edtSearchReceita.editableText.toString()
            viewModel.searchReceita(titleReceita = titleReceita)
            customDialog.dismiss()
        }

        bind.btnSkipReceita.onDebouncedListener{
            customDialog.dismiss()
        }
    }

    private fun showOptionsDialog(receita: Receita){
        val customDialog = AlertDialog.Builder(this).create()
        val bind : DialogActionsReceitaBinding = DialogActionsReceitaBinding.inflate(LayoutInflater.from(this))
        customDialog.apply {
            setView(bind.root)
            setCancelable(true)
        }.show()

        bind.btnFavoritar.onDebouncedListener {
            viewModel.setReceitaAsFavorite(idReceita = receita.id)
            customDialog.dismiss()
        }

        bind.btnDeletar.onDebouncedListener {
            viewModel.deleteReceita(idReceita = receita.id)
            customDialog.dismiss()
        }

        bind.btnEditar.onDebouncedListener {
            //viewModel.editReceita(idReceita = receita.id)
            customDialog.dismiss()
        }

    }

    private fun openFotosScreen(idReceita: Int){
        val intent = Intent(this, ExibirActivity::class.java)
        intent.putExtra("idReceita", idReceita)
        startActivity(intent)
    }

    private fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}