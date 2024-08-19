package com.example.countriessdk;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView countryFlag;
    private TextView countryCode;
    private TextView countryName;
    private TextView countryCurrency;

    private Button selectCountry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryFlag = findViewById(R.id.country_flag);
        countryCode = findViewById(R.id.country_code);
        countryName = findViewById(R.id.country_name);
        countryCurrency = findViewById(R.id.currency);
        selectCountry = findViewById(R.id.select_country);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name = data.getStringExtra("name");
                            String prefix = data.getStringExtra("prefix");
                            String currency = data.getStringExtra("currency");
                            String flag = data.getStringExtra("flag");

                            String countryPrefix = prefix.replaceAll("\\[", "").replaceAll("\\]", "");

                            countryName.setText(name);
                            countryCode.setText(countryPrefix);
                            countryCurrency.setText(currency);
                            Glide.with(this)
                                    .load(flag)
                                    .into(countryFlag);
                        }
                    }
                }
        );

        selectCountry.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CountriesActivity.class);
            activityResultLauncher.launch(intent);
        });
    }
}
