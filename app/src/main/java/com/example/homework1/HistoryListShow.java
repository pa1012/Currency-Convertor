package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryListShow extends AppCompatActivity {

  private ListView historyListView;
  HistoryList historyList;
  HistoryAdapter historyAdapter = null;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_history_list_show);

    historyListView = findViewById(R.id.historyList);

    Intent intent = getIntent();
    historyList = (HistoryList) intent.getSerializableExtra("History");

    ArrayList<HistoryConvert> historyConverts = historyList.list;
    historyAdapter = new HistoryAdapter(this,0,historyConverts);
    historyListView.setAdapter(historyAdapter);


  }
}