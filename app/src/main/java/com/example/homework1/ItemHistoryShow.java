package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemHistoryShow extends AppCompatActivity {

  HistoryConvert historyConvert;
  TextView amountTextView ;
  ArrayList<CurrencyInfo> targetCurrencyList;
  TargetCurrencyAdapter currencyAdapter;
  ListView currencyListView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_history_show);

    Intent intentIn = getIntent();
    historyConvert = (HistoryConvert) intentIn.getSerializableExtra("HistoryItem");
    TextView textView = findViewById(R.id.textViewAmountBC);

    textView.setText(historyConvert.getAmountBC());

    double amount = Double.parseDouble(historyConvert.getAmountBC());

    targetCurrencyList = historyConvert.getTargetCurrencyList();
    currencyAdapter = new TargetCurrencyAdapter(this,0,targetCurrencyList);
    currencyAdapter.setAmount(amount);

    currencyListView = findViewById(R.id.listViewTargetCurrency);
    currencyListView.setAdapter(currencyAdapter);
  }
}