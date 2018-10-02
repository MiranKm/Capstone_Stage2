package com.example.miranpc.knowmore.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.miranpc.knowmore.Adapters.SavedFactsAdapter;
import com.example.miranpc.knowmore.DataBase.AppExecutors;
import com.example.miranpc.knowmore.DataBase.FactDB;
import com.example.miranpc.knowmore.FactViewModel;
import com.example.miranpc.knowmore.Model.Fact;
import com.example.miranpc.knowmore.R;

import java.util.List;

public class SavedActivity extends AppCompatActivity implements SavedFactsAdapter.OnItemClicked {
    private static final String TAG = "SavedActivity";
    RecyclerView savedFactsRecycler;


    FactDB factDB;
    SavedFactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        savedFactsRecycler = findViewById(R.id.saved_facts_recycler);

        MakeRecycler();
        factDB = FactDB.getInstance(this);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int pos = viewHolder.getAdapterPosition();
                        final List<Fact> list = adapter.getFact();
                        factDB.factDao().deleteFact(list.get(pos));
                        runOnUiThread(() -> {
                            adapter.clearList();
                            adapter.setFacts(list);
                        });
                    }
                });
            }
        }).attachToRecyclerView(savedFactsRecycler);


        FactViewModel factViewModel = new FactViewModel(getApplication());
        factViewModel.getFactEntityLiveData().observe(this, facts -> {

            adapter.setFacts(facts);

        });

    }


    private void MakeRecycler() {

        adapter = new SavedFactsAdapter(this, this);

        savedFactsRecycler.setHasFixedSize(true);
        savedFactsRecycler.setLayoutManager(new LinearLayoutManager(this));
        savedFactsRecycler.setAdapter(adapter);

    }

    @Override
    public void ItemClicked(int pos) {
    }
}
