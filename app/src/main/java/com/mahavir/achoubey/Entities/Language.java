package com.mahavir.achoubey.Entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Language implements Serializable {
    @SerializedName("iso639_1")
    private String isoOne;
    @SerializedName("iso639_2")
    private String isoTwo;
    @SerializedName("name")
    private String name;
    @SerializedName("nativeName")
    private String nativeName;

    public String getIsoOne() {
        return isoOne;
    }

    public void setIsoOne(String isoOne) {
        this.isoOne = isoOne;
    }

    public String getIsoTwo() {
        return isoTwo;
    }

    public void setIsoTwo(String isoTwo) {
        this.isoTwo = isoTwo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
