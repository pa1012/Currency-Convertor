package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    final Intent intent = getIntent();
    historyList = (HistoryList) intent.getSerializableExtra("History");

    final ArrayList<HistoryConvert> historyConverts = historyList.list;
    historyAdapter = new HistoryAdapter(this,0,historyConverts);
    historyListView.setAdapter(historyAdapter);

    historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Intent intentOut = new Intent(HistoryListShow.this,ItemHistoryShow.class);

        HistoryConvert historyConvert = historyConverts.get(pos);
        intentOut.putExtra("HistoryItem", historyConvert);
        startActivity(intentOut);
      }
    });
  }
}