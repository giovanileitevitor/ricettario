package br.applabbs.pixells.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import br.applabbs.pixells.R
import br.applabbs.pixells.databinding.ActivitySplashPixelBinding
import br.applabbs.pixells.ui.home.HomePixelActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashPixelActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashPixelBinding
    private val viewModel: SplashPixelViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivitySplashPixelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.FullscreenTheme)
        setupView()
        setupObservers()
    }

    private fun setupView(){
        viewModel.getWaitProcess()
    }

    private fun setupObservers(){
        viewModel.next.observe(this) { _ ->
            val intent = Intent(this, HomePixelActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}