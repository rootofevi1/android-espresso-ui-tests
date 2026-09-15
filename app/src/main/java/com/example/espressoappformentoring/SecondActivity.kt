package com.example.espressoappformentoring;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.espressoappformentoring.databinding.ActivitySecondBinding;

/**
 * Вторая Activity приложения. Открывается из первого фрагмента и показывает
 * отдельный экран с текстом.
 */
public class SecondActivity : AppCompatActivity()  {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
