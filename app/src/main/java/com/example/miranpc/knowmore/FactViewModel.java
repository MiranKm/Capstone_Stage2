package com.example.miranpc.knowmore;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.miranpc.knowmore.DataBase.FactDB;
import com.example.miranpc.knowmore.Model.Fact;

import java.util.List;

public class FactViewModel extends AndroidViewModel {


    private LiveData<List<Fact>> factEntityLiveData;

    public FactViewModel(@NonNull Application application) {
        super(application);

        FactDB factDB = FactDB.getInstance(getApplication().getApplicationContext());
        factEntityLiveData = factDB.factDao().loadAllFacts();
    }

    public LiveData<List<Fact>> getFactEntityLiveData() {
        return factEntityLiveData;
    }


}
