package com.example.miranpc.knowmore.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.miranpc.knowmore.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIfStarted();
        started();
    }

    private void checkIfStarted() {
        SharedPreferences preferences = getDefaultSharedPreferences(this);
        boolean started = preferences.getBoolean("started", false);
        if (started==false){
        }
        else{
            startActivity(new Intent(this, CategoriesActivity.class));

        }
    }


    @Override
    protected void onResume() {
        finish();
        super.onResume();
    }

    private void started() {
        SharedPreferences preferences = getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("started", true);
        editor.apply();
    }


    public void GotoCategories(View view) {
        startActivity(new Intent(this, CategoriesActivity.class));

    }
}
