package com.example.homework1;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TargetCurrencyAdapter extends ArrayAdapter<CurrencyInfo> {
  private double amount;
  public TargetCurrencyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CurrencyInfo> currencyList) {
    super(context, resource, currencyList);
    this.amount = 0;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    //View vi = convertView;

    CurrencyInfo currencyInfo = getItem(position);

    if (convertView == null) {

      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_target_currency, parent, false);

    }


    ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewTC);
    imageView.setImageResource(currencyInfo.getImage());


    TextView textView = convertView.findViewById(R.id.textViewAbbTC);
    textView.setText(currencyInfo.getCurrencyAbb());

    textView = convertView.findViewById(R.id.textViewNameTC);
    textView.setText(currencyInfo.getCurrencyName());

    textView = convertView.findViewById(R.id.textViewAmountTC);
    double res = amount * currencyInfo.getNumber();
    textView.setText(Double.toString(res));

    return convertView;

  }


}
