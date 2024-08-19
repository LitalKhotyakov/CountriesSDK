package com.example.countriessdk;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CountriesActivity extends AppCompatActivity {
    private CountriesSDk countriesSDk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        countriesSDk = findViewById(R.id.countries);

        countriesSDk.setOnCountrySelectedListener((name, telephonePrefix, currency, flag) -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("prefix", telephonePrefix);
            resultIntent.putExtra("currency", currency);
            resultIntent.putExtra("flag", flag);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
