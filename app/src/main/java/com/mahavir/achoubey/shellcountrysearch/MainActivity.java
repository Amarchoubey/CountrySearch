package com.mahavir.achoubey.shellcountrysearch;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mahavir.achoubey.Adapter.SearchAdapter;
import com.mahavir.achoubey.Entities.CountryData;
import com.mahavir.achoubey.Entities.Currencies;
import com.mahavir.achoubey.Entities.Language;
import com.mahavir.achoubey.Helpers.ConnectionUtils;
import com.mahavir.achoubey.Helpers.CountryDBHelper;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    List<CountryData> countryDataValue = new ArrayList<>();
    private CountryDBHelper dbHelper;

    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check Network connectivity
        isConnected = ConnectionUtils.isConnected(this);

        // create UI
        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Enter search");
        recyclerView = findViewById(R.id.listView);

        //Initialize DB
        dbHelper = new CountryDBHelper(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String searchQuery) {

                if (isConnected) {
                    new GetCountry(searchQuery).execute();
                } else {
                    countryDataValue = searchDictionaryWords(searchQuery);
                    setCountryAdapter();
                }

                return true;
            }
        });
    }

    public void doStuff(CountryData countryData) {

        Intent intent = new Intent(this, CountryDetailActivity.class);
        //Create the bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("countryDetail", countryData);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private class GetCountry extends AsyncTask<Void, Void, Void> {

        // URL to get contacts JSON
        private String url = "https://restcountries.eu/rest/v2/name/";

        public GetCountry(String urlString) {
            url = url + urlString;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = httpHandler.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try{
                    Gson gson = new Gson();

                    JsonArray countryList = gson.fromJson(jsonStr, JsonArray.class);

                    List<CountryData> countryDataList = new ArrayList<>();
                    for (int i = 0; i < countryList.size(); i++) {

                        JsonObject jsonObject = countryList.get(i).getAsJsonObject();
                        CountryData countryData = new CountryData();
                        countryData.setName(jsonObject.get("name").toString());
                        countryData.setFlag(jsonObject.get("flag").toString());
                        countryData.setCapital(jsonObject.get("capital").toString());
                        countryData.setRegion(jsonObject.get("region").toString());
                        countryData.setSubregion(jsonObject.get("subregion").toString());

                        //Get calling Codes related data
                        JsonArray sectionElement = jsonObject.getAsJsonArray("callingCodes");
                        final List<String> callingList = new ArrayList<>();
                        for (int j = 0;j<sectionElement.size(); j++) {
                            callingList.add(sectionElement.get(j).toString());
                        }
                        countryData.setCallingCodes(callingList);

                        //Get time zone data
                        JsonArray timeZoneArray = jsonObject.getAsJsonArray("timezones");
                        final List<String> timeList = new ArrayList<>();
                        for (int m = 0;m<timeZoneArray.size(); m++) {
                            timeList.add(timeZoneArray.get(m).toString());
                        }
                        countryData.setTimezones(timeList);

                        //Get currencies
                        JsonArray currencyArray = jsonObject.getAsJsonArray("currencies");
                        Currencies currencies = new Currencies();
                        final List<Currencies> currencyList = new ArrayList<>();

                        for (int m = 0; m < currencyArray.size(); m++) {
                            JsonObject jObject = currencyArray.get(m).getAsJsonObject();
                            currencies.setCode(jObject.get("code").toString());
                            currencies.setName(jObject.get("name").toString());
                            currencies.setSymbol(jObject.get("symbol").toString());
                        }

                        currencyList.add(currencies);
                        countryData.setCurrencies(currencyList);

                        //Get languages
                        JsonArray languageArray = jsonObject.getAsJsonArray("languages");
                        Language language = new Language();
                        final List<Language> languageList = new ArrayList<>();

                        for (int n = 0; n < languageArray.size(); n++) {
                            JsonObject jsonObject1 = languageArray.get(n).getAsJsonObject();
                            language.setName(jsonObject1.get("name").toString());
                        }
                        languageList.add(language);
                        countryData.setLanguages(languageList);

                        //Fill the list with country flag, country name, capital, calling code, region, sub region, time zone,
                        //currencies and languages.
                        countryDataList.add(countryData);
                        countryDataValue = countryDataList;
                    }

                    System.out.println(countryDataValue);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Data Found ", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setCountryAdapter();
        }
    }

    protected void setCountryAdapter() {
        searchAdapter = new SearchAdapter(MainActivity.this, countryDataValue);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.setClickListener(new SearchAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG, "onItemClick position: " + position);
                CountryData countryData = countryDataValue.get(position);
                doStuff(countryData);
            }
        });
    }

    public List<CountryData> searchDictionaryWords(String searchWord){
        List<CountryData> countryData = new ArrayList<>();
        Cursor cursor = dbHelper.getCountry(searchWord);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String flag = cursor.getString(cursor.getColumnIndexOrThrow("flag"));
                String capital = cursor.getString(cursor.getColumnIndexOrThrow("capital"));
                String region = cursor.getString(cursor.getColumnIndexOrThrow("region"));
                String subregion = cursor.getString(cursor.getColumnIndexOrThrow("subregion"));
                String callingcode = cursor.getString(cursor.getColumnIndexOrThrow("callingcode"));
                String timezone = cursor.getString(cursor.getColumnIndexOrThrow("timezone"));
                String currency = cursor.getString(cursor.getColumnIndexOrThrow("currency"));
                String language = cursor.getString(cursor.getColumnIndexOrThrow("language"));

                CountryData cdata = new CountryData();
                cdata.setName(name);
                cdata.setFlag(flag);
                cdata.setCapital(capital);
                cdata.setRegion(region);
                cdata.setSubregion(subregion);

                //Add calling code
                List<String> list = new ArrayList<>();
                list.add(callingcode);
                cdata.setCallingCodes(list);

                //Add timezone
                List<String> listTime = new ArrayList<>();
                listTime.add(timezone);
                cdata.setTimezones(listTime);

                //Add currency
                List<Currencies> currenciesList = new ArrayList<>();
                Currencies currencies = new Currencies();
                currencies.setName(currency);
                currenciesList.add(currencies);
                cdata.setCurrencies(currenciesList);

                //Add language
                List<Language> languageList = new ArrayList<>();
                Language language1 = new Language();
                language1.setName(language);
                languageList.add(language1);
                cdata.setLanguages(languageList);

                countryData.add(cdata);
            }while(cursor.moveToNext());
        }

        cursor.close();
        return countryData;
    }
}
