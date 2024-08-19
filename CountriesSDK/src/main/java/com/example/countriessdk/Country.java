package com.example.countriessdk;

import java.util.Map;

public class Country {
    private String name;
    private String flag;
    private CountryResponse.Idd idd;
    private Map<String, CountryResponse.Currency> currencies;
    private String region;

    public Country() {
    }


    public Country(String name, String flag, CountryResponse.Idd idd, Map<String, CountryResponse.Currency> currencies, String region) {
        this.name = name;
        this.flag = flag;
        this.idd = idd;
        this.currencies = currencies;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public CountryResponse.Idd getIdd() {
        return idd;
    }

    public void setIdd(CountryResponse.Idd idd) {
        this.idd = idd;
    }

    public Map<String, CountryResponse.Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, CountryResponse.Currency> currencies) {
        this.currencies = currencies;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}



