package com.example.placekittenplaceholderimages.UI;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.placekittenplaceholderimages.Data.KittenItem;
import com.example.placekittenplaceholderimages.databinding.KittenDetailsBinding;

public class KittenDetailsFragment extends Fragment {



   KittenItem selected;

    public KittenDetailsFragment(KittenItem k) {

        selected = k;


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        KittenDetailsBinding binding = KittenDetailsBinding.inflate(inflater);


        binding.getRoot().setBackgroundColor(Color.WHITE);
        binding.kittenDetailsId.setText("Id = " + selected.id);
        binding.kittenDetailsWidth.setText("width : " + selected.getWidth());
        binding.kittenDetailsHeight.setText("height : " + selected.getHeight());
        binding.kittenDetailsDate.setText("Date : " + selected.getDate());
        binding.weatherDetailsIcon.setImageBitmap(BitmapFactory.decodeFile(selected.getImagePath()));


        return binding.getRoot();


    }
}