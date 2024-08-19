package com.example.countriessdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CountriesSDk extends LinearLayout {

    private final List<Country> countries = new ArrayList<>();
    private final List<Country> filteredCountries = new ArrayList<>();
    private CountryAdapter countryAdapter;

    public interface OnCountrySelectedListener {
        void onCountrySelected(String name, String telephonePrefix, String currency, String flag);
    }

    private OnCountrySelectedListener listener;

    public CountriesSDk(Context context) {
        super(context);
        init(context, null);
    }

    public CountriesSDk(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    // For using SDK
    public void setOnCountrySelectedListener(OnCountrySelectedListener listener) {
        this.listener = listener;
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.dialog_country_selection, this, true);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCountries);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        countryAdapter = new CountryAdapter(filteredCountries, this::showCountryInfoDialog);
        recyclerView.setAdapter(countryAdapter);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCountries();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Spinner spinnerRegion = findViewById(R.id.spinnerRegion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.regions_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(adapter);
        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterCountries();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fetchCountriesFromServer();
    }

    private void fetchCountriesFromServer() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        CountryApi apiService = retrofit.create(CountryApi.class);
        Call<List<CountryResponse>> call = apiService.getAllCountries();

        call.enqueue(new Callback<List<CountryResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<CountryResponse>> call, Response<List<CountryResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (CountryResponse countryResponse : response.body()) {
                        Country country = new Country();
                        country.setName(countryResponse.getName().getCommon());
                        country.setFlag(countryResponse.getFlags().getPng());
                        country.setIdd(countryResponse.getIdd());
                        country.setCurrencies(countryResponse.getCurrencies());
                        country.setRegion(countryResponse.getRegion());
                        if (!country.getName().contains("Palestine")) { // removing non exsisting contry
                            countries.add(country);
                        }
                        filteredCountries.clear();
                        filteredCountries.addAll(countries);
                        countryAdapter.addAll(countries);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryResponse>> call, Throwable t) {
                Log.v("Fetching countries", "Failed to fetch");
            }
        });
    }

    private void filterCountries() {
        EditText editTextSearch = findViewById(R.id.editTextSearch);
        Spinner spinnerRegion = findViewById(R.id.spinnerRegion);

        String query = editTextSearch.getText().toString().toLowerCase().trim();
        String selectedRegion = spinnerRegion.getSelectedItem().toString();

        filteredCountries.clear();

        for (Country country : countries) {
            boolean matchesQuery = country.getName().toLowerCase().startsWith(query);
            boolean matchesRegion = selectedRegion.equals("All") || country.getRegion().equalsIgnoreCase(selectedRegion);

            if (matchesQuery && matchesRegion) {
                filteredCountries.add(country);
            }
        }

        countryAdapter.addAll(filteredCountries);
    }

    @SuppressLint("SetTextI18n")
    private void showCountryInfoDialog(Country country) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Country Information");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_country_info, null);
        builder.setView(dialogView);

        ImageView flagImageView = dialogView.findViewById(R.id.flagImageView);
        TextView nameTextView = dialogView.findViewById(R.id.nameTextView);
        TextView phoneTextView = dialogView.findViewById(R.id.phoneTextView);
        TextView currencyTextView = dialogView.findViewById(R.id.currencyTextView);

        Glide.with(getContext())
                .load(country.getFlag())
                .into(flagImageView);

        String phone = country.getIdd().getRoot() + country.getIdd().getSuffixes();

        nameTextView.setText("Selected: " + country.getName());
        phoneTextView.setText("Phone: " + phone);

        if (!country.getCurrencies().isEmpty()) {
            CountryResponse.Currency currency = country.getCurrencies().values().iterator().next();
            String curr = currency.getName() + " (" + currency.getSymbol() + ")";
            currencyTextView.setText("Currency: " + curr);
            if (listener != null) {
                listener.onCountrySelected(country.getName(), phone, curr, country.getFlag());
            }
        } else {
            currencyTextView.setText("Currency: N/A");
        }

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
