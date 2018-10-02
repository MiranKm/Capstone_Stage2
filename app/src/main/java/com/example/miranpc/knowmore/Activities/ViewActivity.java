package com.example.miranpc.knowmore.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miranpc.knowmore.DataBase.AppExecutors;
import com.example.miranpc.knowmore.DataBase.FactDB;
import com.example.miranpc.knowmore.Model.Fact;
import com.example.miranpc.knowmore.R;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    private static final String TAG = "ViewActivity";
    public static final String CATEGORY_ITEM = "ITEM";
    public static final String API_LINK = "https://www.adeptgenerators.com/data.json";
    getDataAsyncTask viewActivity = new getDataAsyncTask();
    public static String itemToShow = "null";

    FactDB factDb;

    static TextView factTV;
    static TextView resourceTV;
    static ProgressBar loading;
    ArrayList<Fact> factArrayList = null;
    static ArrayList<Fact> facts = new ArrayList<>();
    public static int factCounter = 0;
    FloatingActionButton save;

    private boolean isSaved;
    int itemCounted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        factDb = FactDB.getInstance(this);

        factTV = findViewById(R.id.fact_tv);
        save = findViewById(R.id.save);
        loading = findViewById(R.id.loading);
        resourceTV = findViewById(R.id.resource_tv);
        String itemClicked = getIntent().getStringExtra(CATEGORY_ITEM);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setTitle(itemClicked.toUpperCase());
        if (!itemClicked.isEmpty()) {
            itemToShow = itemClicked;
            viewActivity.execute();
        }
    }

    public void saveFact(View view) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            factDb.factDao().insertFact(facts.get(factCounter));
            runOnUiThread(() -> Toast.makeText(ViewActivity.this, "Fact Added to My Facts", Toast.LENGTH_SHORT).show());
        });

    }

    public void nextFact(View view) {
        if (factCounter != facts.size() - 1) {
            factCounter++;
            resourceTV.setText(facts.get(factCounter).getResource());
            factTV.setText(facts.get(factCounter).getFact());
        }


    }

    public void previousFact(View view) {
        if (factCounter != 0) {
            factCounter--;
            resourceTV.setText(facts.get(factCounter).getResource());
            factTV.setText(facts.get(factCounter).getFact());
        }
    }

    public static class getDataAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            BufferedReader reader;
            StringBuffer buffer;
            String res = null;

            try {
                URL url = new URL(API_LINK);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setReadTimeout(40000);
                con.setConnectTimeout(40000);
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                int status = con.getResponseCode();
                InputStream inputStream;
                if (status == HttpURLConnection.HTTP_OK) {
                    inputStream = con.getInputStream();
                } else {
                    inputStream = con.getErrorStream();
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                res = buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            loading.setVisibility(View.GONE);
            try {
                facts = factParsing(s);
                resourceTV.setText(facts.get(factCounter).getResource());
                factTV.setText(facts.get(0).getFact());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private static ArrayList<Fact> factParsing(String s) throws JSONException {
        ArrayList<Fact> facts = new ArrayList<>();
        JSONObject root = new JSONObject(s);
        JSONArray chosenTopic = root.getJSONArray(itemToShow);
        for (int i = 0; i < chosenTopic.length(); i++) {
            JSONObject techRoot = chosenTopic.getJSONObject(i);
            String topic = techRoot.getString("topic");
            String resource = techRoot.getString("resource");
            String fact = techRoot.getString("fact");
            int id = techRoot.getInt("id");
            facts.add(new Fact(id, topic, resource, fact));
        }

        return facts;
    }

    public void gotoMyFact(View view) {
        startActivity(new Intent(this, SavedActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();
        }
        return true;
    }
}
