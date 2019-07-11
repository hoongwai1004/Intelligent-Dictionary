package com.cat300.ty_ch.intelligentdictionary;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private RecyclerView.LayoutManager historyLayoutManager;
    private List<String> historyData = new ArrayList<>();
    private List<String> reversaData = new ArrayList<>();
    HistoryDBHelper historyDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyDBHelper = new HistoryDBHelper(this);

        populateList();
        RecyclerView historyRecyclerView = (RecyclerView)findViewById(R.id.historyView);
        historyRecyclerView.setHasFixedSize(true);
        historyLayoutManager = new LinearLayoutManager(this);
        historyRecyclerView.setLayoutManager(historyLayoutManager);
        HistoryAdapter historyAdapter = new HistoryAdapter(historyData, this);
        historyRecyclerView.setAdapter(historyAdapter);
    }

    private void populateList() {
        Cursor data = historyDBHelper.getAllData();
        while(data.moveToNext()) {
            reversaData.add(data.getString(1));
        }

        for(int i = reversaData.size() - 1; i >= 0; i--)
            historyData.add(reversaData.get(i));
    }

}
