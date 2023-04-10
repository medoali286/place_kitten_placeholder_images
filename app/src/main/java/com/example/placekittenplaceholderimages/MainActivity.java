package com.example.placekittenplaceholderimages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.placekittenplaceholderimages.UI.GetKittenImage;
import com.example.placekittenplaceholderimages.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.kitten.setOnClickListener(c->{
            Intent weatherNowPage = new Intent(this, GetKittenImage.class);

            startActivity(weatherNowPage);



        });



    }
}