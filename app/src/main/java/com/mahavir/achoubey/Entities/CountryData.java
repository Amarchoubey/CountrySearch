package com.mahavir.achoubey.Entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CountryData implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("topLevelDomain")
    List<String> topLevelDomain = new ArrayList<>();
    @SerializedName("alpha2Code")
    private String alpha2Code;
    @SerializedName("alpha3Code")
    private String alpha3Code;
    @SerializedName("callingCodes")
    List<String> callingCodes = new ArrayList<>();
    @SerializedName("capital")
    private String capital;
    @SerializedName("altSpellings")
    List<String> altSpellings = new ArrayList<>();
    @SerializedName("region")
    private String region;
    @SerializedName("subregion")
    private String subregion;
    @SerializedName("population")
    private float population;
    @SerializedName("latlng")
    List<String> latlng = new ArrayList<>();
    @SerializedName("demonym")
    private String demonym;
    @SerializedName("area")
    private float area;
    @SerializedName("gini")
    private String gini;
    @SerializedName("timezones")
    List<String> timezones = new ArrayList<>();
    @SerializedName("borders")
    List<String> borders = new ArrayList<>();
    @SerializedName("nativeName")
    private String nativeName;
    @SerializedName("numericCode")
    private String numericCode;
    @SerializedName("currencies")
    List<Currencies> currencies = new ArrayList<>();
    @SerializedName("languages")
    List<Language> languages = new ArrayList<>();
    @SerializedName("translations")
    List<String> translations = new ArrayList<>();
    @SerializedName("flag")
    private String flag;
    @SerializedName("regionalBlocs")
    List<RegionalBlocks> regionalBlocksList = new ArrayList<>();
    @SerializedName("cioc")
    private String cioc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTopLevelDomain() {
        return topLevelDomain;
    }

    public void setTopLevelDomain(List<String> topLevelDomain) {
        this.topLevelDomain = topLevelDomain;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public List<String> getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(List<String> callingCodes) {
        this.callingCodes = callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public void setAltSpellings(List<String> altSpellings) {
        this.altSpellings = altSpellings;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public float getPopulation() {
        return population;
    }

    public void setPopulation(float population) {
        this.population = population;
    }

    public List<String> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<String> latlng) {
        this.latlng = latlng;
    }

    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getGini() {
        return gini;
    }

    public void setGini(String gini) {
        this.gini = gini;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public List<Currencies> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currencies> currencies) {
        this.currencies = currencies;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<RegionalBlocks> getRegionalBlocksList() {
        return regionalBlocksList;
    }

    public void setRegionalBlocksList(List<RegionalBlocks> regionalBlocksList) {
        this.regionalBlocksList = regionalBlocksList;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }
}

