package com.techelevator.countrylanguage;

public class CountryLanguage {
    private String countryCode;
    private boolean isOfficial;
    private String language;
    private double percentage;


    public CountryLanguage(String countryCode, boolean isOfficial, String language, double percentage) {
        this.countryCode = countryCode;
        this.isOfficial = isOfficial;
        this.language = language;
        this.percentage = percentage;
    }

    public CountryLanguage() {

    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isOfficial() {
        return isOfficial;
    }

    public void setOfficial(boolean official) {
        isOfficial = official;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
