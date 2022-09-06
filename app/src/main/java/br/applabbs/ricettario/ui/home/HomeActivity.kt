package br.applabbs.ricettario.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.aux.emptyReceita
import br.applabbs.ricettario.aux.onDebouncedListener
import br.applabbs.ricettario.databinding.ActivityHomeBinding
import br.applabbs.ricettario.domain.local.models.Receita
import br.applabbs.ricettario.domain.local.models.Step
import br.applabbs.ricettario.ui.adicionar.AdicionarActivity
import br.applabbs.ricettario.ui.configurar.ConfigurarActivity
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

    private val viewModel : HomeViewModel by viewModel()
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
            val intent = Intent(this, AdicionarActivity::class.java)
            startActivity(intent)
        }

        binding.btnSearch.onDebouncedListener {
            showDialogSearch()
        }

        binding.btnConfig.onDebouncedListener {
            val intent = Intent(this, ConfigurarActivity::class.java)
            startActivity(intent)
        }

        binding.btnFotos.onDebouncedListener{
            showToast("Funcionalidade em desenvolvimento")
        }

    }

    private fun setupObservers(){
        viewModel.getReceitas.observe(this, Observer { it ->
            if(it.isEmpty()){
                setupAdapterReceitas(emptyReceita())
            }else{
                setupAdapterReceitas(it)
            }
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.loadingBox.root.isVisible = !isLoading
        })
    }

    private fun setupAdapterReceitas(receitas : List<Receita>){
        binding.rvReceitas.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        receitasAdapter = ReceitasAdapter(receitas, Glide.with(this), singleClick, longClick)
        binding.rvReceitas.adapter = receitasAdapter
        binding.containerDetalhesReceita.isVisible = false
    }

    private val singleClick= { receita: Receita ->
        binding.containerDetalhesReceita.isVisible = true
        binding.txtTitleReceita.text = receita.titulo
        setupAdapterDetalhesReceita(steps = receita.steps)
    }

    private val longClick = { receita: Receita ->
        showToast("Funcionalidade em Desenvolvimento")
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
        showToast("Funcionalidade em Desenvolvimento")
    }

    private fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}