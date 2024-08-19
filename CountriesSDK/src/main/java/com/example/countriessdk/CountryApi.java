package com.example.countriessdk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountryApi {
    @GET("all?fields=name,flags,idd,currencies,region")
    Call<List<CountryResponse>> getAllCountries();

}
