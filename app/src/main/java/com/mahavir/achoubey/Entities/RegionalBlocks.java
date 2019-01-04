package com.mahavir.achoubey.Entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegionalBlocks implements Serializable {

    @SerializedName("acronym")
    private String acronym;

    @SerializedName("name")
    private String name;

    @SerializedName("otherAcronyms")
    private String otherAcronyms;

    @SerializedName("otherNames")
    List<String> list = new ArrayList<>();

}
