package com.example.miranpc.knowmore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miranpc.knowmore.Activities.ViewActivity;
import com.example.miranpc.knowmore.DataBase.FactDB;
import com.example.miranpc.knowmore.Model.Fact;

import java.util.ArrayList;


public class ViewDetailsFragment extends Fragment {


    private static final String TAG = "ViewActivity";
    public static final String CATEGORY_ITEM = "ITEM";
    public static final String API_LINK = "https://www.adeptgenerators.com/data.json";
    public static String itemToShow = "null";

    FactDB factDb;

    TextView factTV, resourceTV;
    ProgressBar loading;
    ArrayList<Fact> factArrayList = null;
    ArrayList<Fact> facts = new ArrayList<>();
    public static int factCounter = 0;
    private boolean isSaved;
    int itemCounted;

    public ViewDetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getContext(), "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.save:
                Toast.makeText(getContext(), "save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.saved:
                Toast.makeText(getContext(), "saved to the save", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
