package com.mahavir.achoubey.shellcountrysearch;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.mahavir.achoubey.Entities.CountryData;
import com.mahavir.achoubey.Helpers.CountryDBHelper;
import com.mahavir.achoubey.Helpers.SvgDecoder;
import com.mahavir.achoubey.Helpers.SvgDrawableTranscoder;
import com.mahavir.achoubey.Helpers.SvgSoftwareLayerSetter;

import java.io.InputStream;

public class CountryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private CountryData countryData;
    private Button saveOffine;
    private ImageView countryFlag;
    private TextView countryName;
    private TextView capital;
    private TextView callingCode;
    private TextView region;
    private TextView subRegion;
    private TextView timeZone;
    private TextView currency;
    private TextView language;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    private CountryDBHelper dbHelper;

    private String nameData;
    private String flagData;
    private String capitalData;
    private String regionData;
    private String subregionData;
    private String callingcodeData;
    private String timezoneData;
    private String currencyData;
    private String languageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        dbHelper = new CountryDBHelper(this);

        getCountryData();

        countryFlag = findViewById(R.id.imgcountryFlag);
        countryName = findViewById(R.id.txtCountryName);
        capital = findViewById(R.id.txtCapital);
        callingCode = findViewById(R.id.txtCallingCode);
        region = findViewById(R.id.txtRegion);
        subRegion = findViewById(R.id.txtSubRegion);
        timeZone = findViewById(R.id.txtTimeZone);
        currency = findViewById(R.id.txtCurrencies);
        language = findViewById(R.id.txtLanguage);
        saveOffine = findViewById(R.id.btnSaveOffline);
        saveOffine.setOnClickListener(this);

        if (countryData != null) {
            setCountryDetail();
        }

    }

    protected void getCountryData() {
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        countryData = (CountryData)bundle.getSerializable("countryDetail");
        if (countryData != null) {
            nameData = countryData.getName().replaceAll("\"", "");
            flagData = countryData.getFlag();
            capitalData = countryData.getCapital();
            regionData = countryData.getRegion();
            subregionData = countryData.getSubregion();
            callingcodeData = countryData.getCallingCodes().get(0);
            timezoneData = countryData.getTimezones().get(0);
            currencyData = countryData.getCurrencies().get(0).getName();
            languageData = countryData.getLanguages().get(0).getName();
        }
    }

    protected void setCountryDetail() {

        countryName.setText(nameData);
        setImage(flagData);
        capital.setText(capitalData);
        region.setText(regionData);
        subRegion.setText(subregionData);

        callingCode.setText(callingcodeData);
        timeZone.setText(timezoneData);
        currency.setText(currencyData);
        language.setText(languageData);
    }

    protected void setImage(String flagUrl) {

                requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        Uri uri = Uri.parse(flagUrl.replaceAll("\"", ""));

        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(countryFlag);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveOffline:
                saveCountryDetails();
                return;
        }

    }

    protected void saveCountryDetails() {
        if(dbHelper.insertCountry(nameData,flagData,capitalData,regionData,subregionData,
                callingcodeData,timezoneData,currencyData,languageData)) {
            Toast.makeText(getApplicationContext(), "Country Value Succesfully Inserted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Could not Insert Country Data ", Toast.LENGTH_SHORT).show();
        }
    }
}
