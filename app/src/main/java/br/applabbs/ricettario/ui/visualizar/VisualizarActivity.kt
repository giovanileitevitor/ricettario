package br.applabbs.ricettario.ui.visualizar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.applabbs.ricettario.databinding.ActivityVisualizarBinding
import org.koin.android.viewmodel.ext.android.viewModel

class VisualizarActivity: AppCompatActivity() {

    private val viewModel : VisualizarViewModel by viewModel()
    private lateinit var binding: ActivityVisualizarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}