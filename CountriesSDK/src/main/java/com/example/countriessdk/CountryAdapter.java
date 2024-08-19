package com.example.countriessdk;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> implements Filterable {

    public interface OnCountryClickListener {
        void onCountryClick(Country country);
    }

    private final List<Country> countries;
    private final List<Country> filteredCountries;
    private final OnCountryClickListener onCountryClickListener;

    public CountryAdapter(List<Country> countries, OnCountryClickListener onCountryClickListener) {
        this.countries = countries;
        this.filteredCountries = new ArrayList<>(countries);
        this.onCountryClickListener = onCountryClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAll(List<Country> countries) {
        filteredCountries.clear();
        filteredCountries.addAll(countries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.bind(filteredCountries.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredCountries.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint != null ? constraint.toString().toLowerCase().trim() : "";
                List<Country> filteredList = new ArrayList<>();
                if (query.isEmpty()) {
                    filteredList.addAll(countries);
                } else {
                    for (Country country : countries) {
                        if (country.getName().toLowerCase().startsWith(query)) {
                            filteredList.add(country);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCountries.clear();
                filteredCountries.addAll((List<Country>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {
        private final TextView countryName;
        private final ImageView countryFlag;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.countryName);
            countryFlag = itemView.findViewById(R.id.countryFlag);

            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onCountryClickListener.onCountryClick(filteredCountries.get(position));
                }
            });
        }

        public void bind(Country country) {
            countryName.setText(country.getName());
            Glide.with(itemView.getContext())
                    .load(country.getFlag())
                    .into(countryFlag);
        }
    }
}
