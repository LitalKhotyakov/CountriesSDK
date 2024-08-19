package com.example.countriessdk;

import java.util.List;
import java.util.Map;

public class CountryResponse {

    private Name name;
    private Flags flags;
    private Idd idd;
    private Map<String, Currency> currencies;
    private String region;

    public Name getName() {
        return name;
    }

    public Flags getFlags() {
        return flags;
    }

    public Idd getIdd() {
        return idd;
    }

    public Map<String, Currency> getCurrencies() {
        return currencies;
    }

    public String getRegion() {
        return region;
    }

    public static class Name {
        private String common;
        private String official;

        public String getCommon() {
            return common;
        }

        public String getOfficial() {
            return official;
        }
    }

    public static class Flags {
        private String png;

        public String getPng() {
            return png;
        }
    }

    public static class Idd {
        private String root;
        private List<String> suffixes;

        public String getRoot() {
            return root;
        }

        public List<String> getSuffixes() {
            return suffixes;
        }
    }

    public static class Currency {
        private String name;
        private String symbol;

        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }

        @Override
        public String toString() {
            return name + " (" + symbol + ")";
        }
    }
}


