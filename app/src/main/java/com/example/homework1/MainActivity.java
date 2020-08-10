package com.example.homework1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {

  private static final int NUMBER_OF_ITEMS = 10;
  private static final int NUMBER_OF_EQUAL= 123;

  private  int nextId= 2000;

  private ArrayList<CurrencyInfo> currencyList = null;
  private ArrayList<CurrencyInfo> currencyTargetList = new ArrayList<CurrencyInfo>();

  private ListView currencyTargetListView = null;
  private ListView currencyListView = null;

  private TargetCurrencyAdapter currencyTargetAdapter = null;
  private String amountCalculate ;
  private String expressionText ;

  private static final  int REQ_CODE = 1234;


  Intent intent;
  Intent intentHistory;

  private HistoryList historyList = null;
 // private ArrayList<HistoryConvert> historyList = null;
  private SharedPreferences mPreferences;
  private SharedPreferences.Editor mEditor;

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d("TAG", "onSaveInstanceState: ");
    //outState.putString("expression", expressionText.getText().toString());
    outState.putString("amountBaseCurrency", amountCalculate);
    outState.putString("expression", expressionText);
    outState.putParcelableArrayList("currencyList",currencyList);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    if (savedInstanceState != null)
    {
      amountCalculate = savedInstanceState.getString("amountBaseCurrency");
      expressionText = savedInstanceState.getString("expression");
      if (amountCalculate == null && expressionText == null) {
        amountCalculate = "0.0";
        expressionText = "0";
      }
      TextView textView = (TextView) findViewById(R.id.editTextAmount);
      textView.setText(expressionText);
      currencyList = savedInstanceState.getParcelableArrayList("currencyList");
    }
    else
    {
      currencyList = createCurrencyList(NUMBER_OF_ITEMS);
      amountCalculate = "0.0";
      expressionText = "0";
    }

    currencyTargetListView = findViewById(R.id.listViewTargetCurrency);
    updateTargetCurrencyList();
    currencyTargetAdapter = new TargetCurrencyAdapter(this,0,currencyTargetList);
    currencyTargetListView.setAdapter(currencyTargetAdapter);

    if (amountCalculate != "0.0" ) updateResult(amountCalculate);

    currencyTargetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TextView textView = (TextView) view.findViewById(R.id.textViewAbbTC);
        CurrencyInfo currencyInfo = findCurrency(textView.getText().toString());
        currencyInfo.setInTargetList(false);
        deleteTargetCurrencyList(currencyInfo);

        currencyTargetAdapter.notifyDataSetChanged();

      }
    });


    createButtons(R.id.gridKeyNumber);
    setButtonOperatorHelper(R.id.divideBtn);
    setButtonOperatorHelper(R.id.multiplyBtn);
    setButtonOperatorHelper(R.id.subtractBtn);
    setButtonOperatorHelper(R.id.addBtn);
    setButtonOperatorHelper(R.id.deleteBtn);


    //history setting

    mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    mEditor = mPreferences.edit();

    Gson gson = new Gson();
    String json = mPreferences.getString("History", "");
    historyList = gson.fromJson(json, HistoryList.class);

    if (historyList == null){
      historyList = new HistoryList();
      historyList.list = new ArrayList<>();
    }

    //set helper for button
    Button btn = (Button) findViewById(NUMBER_OF_EQUAL);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        TextView textEx = (TextView) findViewById(R.id.editTextAmount);
        String res = calculate(textEx.getText().toString());
        updateResult(res);
        createHistory();
      }
    });

    Button historyBtn = (Button) findViewById(R.id.historyButton);
    historyBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        intentHistory = new Intent(MainActivity.this, HistoryListShow.class);
        intentHistory.putExtra("History", historyList);
        startActivity(intentHistory);
      }
    });


    Button addButton = (Button) findViewById(R.id.addTargetCurrencyButton);
    addButton.setOnClickListener(helper);

  }

  @Override
  protected void onPause() {
    super.onPause();
    saveHistory();
  }

  @Override
  protected void onStop() {
    super.onStop();
    saveHistory();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    saveHistory();
  }

  private void createHistory(){
    HistoryConvert a = new HistoryConvert();
    a.setAmountBC(amountCalculate);
    a.setTargetCurrencyList(currencyTargetList);
    historyList.add(a);
  }

  private void saveHistory(){
    Gson gson = new Gson();
    String json = gson.toJson(historyList);
    mEditor.putString("History", json);
    mEditor.commit();
  }


  private void updateResult(String res) {
    amountCalculate = res;
    TextView textExp = (TextView) findViewById(R.id.editTextAmount);
    expressionText = textExp.getText().toString();
    //CurrencyInfo currencyInfo = null;
    if (res != "BAD EXPRESSION")
    {
      double amount = Double.parseDouble(res);

      //String targetCurrency = getCurrency();

      TextView textView = null;
      textView = (TextView) findViewById(R.id.textViewAmountBC);
      textView.setText(Double.toString(amount));



      currencyTargetAdapter.setAmount(amount);
      currencyTargetListView.setAdapter(currencyTargetAdapter);
    }
    else
    {

      TextView textView = null;
      textView = (TextView) findViewById(R.id.textViewAmountBC);
      textView.setText("0.0");

      currencyTargetAdapter.setAmount(0);
      currencyTargetListView.setAdapter(currencyTargetAdapter);

    }


  }

  private double calculateAmount(double amount, double number) {
    return amount*number;
  }

  private void deleteTargetCurrencyList(CurrencyInfo currencyInfo) {
    currencyTargetList.remove(currencyInfo);
  }

  private void updateTargetCurrencyList() {
    CurrencyInfo currencyInfo = null;
    for (int i = 0 ;i < currencyList.size(); i++)
    {
      currencyInfo = currencyList.get(i);
      if (currencyInfo.isInTargetList())
      {
        currencyTargetList.add(currencyInfo);
      }
    }

  }


  private void setButtonOperatorHelper(int id) {
    Button button = (Button)findViewById(id);
    button.setOnClickListener(helper);

  }




  private void setTargetCurrency(ArrayList<CurrencyInfo> currencyList, int i, double v) {
    CurrencyInfo currencyInfo = currencyList.get(i);


    currencyInfo.setInTargetList(true);
  }


  private void createButtons(int grid) {
    GridLayout gridLayout = (GridLayout) findViewById(grid);

    gridLayout.removeAllViews();
    String[] labels = initNumberButtonsLabels();


    for (int i = 0; i < labels.length; i++){
      Button button = createButton(labels[i]);
      addButtonToGridView(button,gridLayout);
    }
  }



  private void addButtonToGridView(Button button, GridLayout gridLayout) {
    gridLayout.addView(button);
  }

  private View.OnClickListener helper = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if (view.getId() != R.id.addTargetCurrencyButton){
        TextView text = (TextView) findViewById(R.id.editTextAmount);
        Button btn = (Button) findViewById(view.getId());
        String s = text.getText().toString();
        if (s == "0") {
          s= "";
          text.setText("");
        }
        if (btn.getText().toString() != "=" && btn.getId() != R.id.deleteBtn)
        {
          s = (s+ btn.getText());
        }
        else if (btn.getId() == R.id.deleteBtn && s.length() > 0)
        {
          s = s.substring(0, s.length() - 1);
        }
        text.setText(s);
      }
      else
      {

        intent = new Intent(MainActivity.this, MainActivity2.class);

        intent.putExtra("CurrencyListExtra", currencyList);
       // intent.putExtra("BaseCurrencyExtra", amountCalculate);
       // intent.putExtra("ExpressionExtra", expressionText);

        startActivityForResult(intent,REQ_CODE);

      }

      }
    };

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK){
      if (requestCode == REQ_CODE && data != null){
        currencyList = (ArrayList<CurrencyInfo>)  data.getSerializableExtra("ReturnList");
        currencyTargetList.clear();
        updateTargetCurrencyList();
        updateResult(amountCalculate);
      }
    }
  }

  private void setResultForConvert(String res) {
    double amount = Double.parseDouble(res);

    TextView textAmountBC = (TextView) findViewById(R.id.textViewAmountBC);
    textAmountBC.setText(res);


  }

  public String sumOf2String(String s1, String s2)
  {
    if(s1.equals("BAD EXPRESSION")) return "BAD EXPRESSION";
    if(s2.equals("BAD EXPRESSION")) return "BAD EXPRESSION";
    Double d1=Double.parseDouble(s1);
    Double d2=Double.parseDouble(s2);
    return (String.valueOf(d1+d2));
  }
  public String subOf2String(String s1, String s2)
  {
    if(s1.equals("BAD EXPRESSION")) return "BAD EXPRESSION";
    if(s2.equals("BAD EXPRESSION")) return "BAD EXPRESSION";
    Double d1=Double.parseDouble(s1);
    Double d2=Double.parseDouble(s2);
    return (String.valueOf(d1-d2));
  }
  public String mulOf2String(String s1, String s2)
  {
    if(s1.equals("BAD EXPRESSION")) return "BAD EXPRESSION";
    if(s2.equals("BAD EXPRESSION")) return "BAD EXPRESSION";
    Double d1=Double.parseDouble(s1);
    Double d2=Double.parseDouble(s2);
    return (String.valueOf(d1*d2));
  }
  public String divOf2String(String s1, String s2)
  {
    if(s1.equals("BAD EXPRESSION")) return "BAD EXPRESSION";
    if(s2.equals("BAD EXPRESSION")) return "BAD EXPRESSION";
    Double d1=Double.parseDouble(s1);
    Double d2=Double.parseDouble(s2);
    if (d2==0)
      return "BAD EXPRESSION";
    return (String.valueOf(d1/d2));
  }
  public boolean valid(String input){
    for (int i=0;i<input.length()-1;i++){
      char c1=input.charAt(i), c2=input.charAt(i+1);
      String check1=String.valueOf(c1), check2= String.valueOf(c2);
      if ((check1.equals("+")||check1.equals("-")||check1.equals("x")||check1.equals(":"))&&
              (check2.equals("+")||check2.equals("-")||check2.equals("x")||check2.equals(":")))
        return false;
    }
    String check3=input.substring(0,1);
    String check4=input.substring(input.length()-1);
    if (check3.equals("+")||check3.equals("-")||check3.equals("x")||check3.equals(":"))
      return false;
    if (check4.equals("+")||check4.equals("-")||check4.equals("x")||check4.equals(":"))
      return false;
    return true;
  }
  public String calculate(String input) {
    if (!valid(input)) return "BAD EXPRESSION";
    for (int i = input.length() - 1; i >= 0; i--) {
      char c = input.charAt(i);
      String s = String.valueOf(c);
      if (s.equals("+")) {
        String s1, s2;
        s1 = input.substring(0, i);
        s2 = input.substring(i + 1);
        return (sumOf2String(calculate(s1), calculate(s2)));
      } else if (s.equals("-")) {
        String s1, s2;
        s1 = input.substring(0, i);
        s2 = input.substring(i + 1);
        return (subOf2String(calculate(s1), calculate(s2)));
      }
    }
    for (int i = input.length() - 1; i >= 0; i--) {
      char c = input.charAt(i);
      String s = String.valueOf(c);
      if (s.equals("*")) {
        String s1, s2;
        s1 = input.substring(0, i);
        s2 = input.substring(i + 1);
        return (mulOf2String(calculate(s1), calculate(s2)));
      } else if (s.equals("/")) {
        String s1, s2;
        s1 = input.substring(0, i);
        s2 = input.substring(i + 1);
        return (divOf2String(calculate(s1), calculate(s2)));
      }
    }
    return input;
  }
  private Button createButton(String label) {

    final Button button = new Button(this);
    button.setText(label);

    if (label == "="){
      button.setId(NUMBER_OF_EQUAL);
    }
    else{
      button.setId(nextId);
      nextId++;
      button.setOnClickListener(helper);
    }

    return button;
  }

  private String[] initNumberButtonsLabels() {
    return new String[]{"7","8","9",
                        "4","5","6",
                        "1","2","3",
                        ".","0","="};
  }



  private CurrencyInfo findCurrency(String targetCurrency) {
    for (int i = 0; i < currencyList.size(); i++)
    {
      if (currencyList.get(i).getCurrencyAbb() == targetCurrency)
        return currencyList.get(i);
    }
    return null;
  }


  private ArrayList<CurrencyInfo> createCurrencyList(int n) {
    ArrayList<CurrencyInfo> res = new ArrayList<CurrencyInfo>();
    double rate = 0.1;
    for (int i = 0; i< n; i++) {
      rate = rate + 0.1;
      if (i%2==0)
        res.add(new CurrencyInfo("USD" + String.valueOf(i),"United State Dollar " + String.valueOf(i), rate , R.drawable.flag));
      else
        res.add(new CurrencyInfo("USK" + String.valueOf(i),"UK dollar " + String.valueOf(i), rate , R.drawable.uk));
    }

    return res;
  }
}
