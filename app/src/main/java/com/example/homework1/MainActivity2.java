package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

  private ListView currencyListView = null;
  ArrayList<CurrencyInfo> currencyList = null;

  //String amount="0.0", expression="0";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main3);

    Intent intent = getIntent();

    currencyList = (ArrayList<CurrencyInfo>) intent.getSerializableExtra("CurrencyListExtra");

    currencyListView = findViewById(R.id.listViewCurrency);

    final ArrayList<CurrencyInfo>  currencyInfos = getCurrencyList(currencyList);

    final CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, 0, currencyInfos);
    currencyListView.setAdapter(currencyAdapter);

      currencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TextView textView = (TextView) view.findViewById(R.id.textViewAbbCurrency);
        CurrencyInfo currencyInfo = findCurrency(textView.getText());
        currencyInfo.setInTargetList(true);
        currencyInfos.remove(currencyInfo);
        currencyAdapter.notifyDataSetChanged();
      }
    });

    Button button = findViewById(R.id.buttonBack);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent1 = new Intent();
        intent1.putExtra("ReturnList", currencyList);
        setResult(RESULT_OK, intent1);
        finish();
      }
    });
  }

  private CurrencyInfo findCurrency(CharSequence text) {
    CurrencyInfo currencyInfo = null;
    for (int i = 0 ; i < currencyList.size();i++)
    {
      currencyInfo = currencyList.get(i);
      if (currencyInfo.getCurrencyAbb() == text){
        return currencyInfo;
      }
    }
    return null;
  }

  private ArrayList<CurrencyInfo> getCurrencyList(ArrayList<CurrencyInfo> currencyList) {
    ArrayList<CurrencyInfo> res = new ArrayList<CurrencyInfo>();
    CurrencyInfo currencyInfo = null;
    for (int i = 0; i < currencyList.size(); i++)
    {
      currencyInfo = currencyList.get(i);
      if (!currencyInfo.isInTargetList()){
        res.add(currencyInfo);
      }
    }
  return res;
  }


}