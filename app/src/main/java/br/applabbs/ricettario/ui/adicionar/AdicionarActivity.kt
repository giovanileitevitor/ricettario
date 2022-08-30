package br.applabbs.ricettario.ui.adicionar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.applabbs.ricettario.databinding.ActivityAdicionarBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AdicionarActivity: AppCompatActivity() {

    private val viewModel : AdicionarViewModel by viewModel()
    private lateinit var binding: ActivityAdicionarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}