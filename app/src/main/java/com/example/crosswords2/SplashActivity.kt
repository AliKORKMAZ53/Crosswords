package com.example.crosswords2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 300
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
     /*   ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        // Use Coroutine to delay the start of the next activity
        lifecycleScope.launch {
            delay(SPLASH_DELAY)

            // Start the next activity
            val intent = Intent(this@SplashActivity, ModeSelection::class.java)
            startActivity(intent)
            finish()
        }
    }
}