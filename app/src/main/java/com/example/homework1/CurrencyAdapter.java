package com.example.homework1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CurrencyAdapter extends ArrayAdapter<CurrencyInfo> {
  public CurrencyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CurrencyInfo> currencyList) {
    super(context, resource, currencyList);

  }


  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    CurrencyInfo currencyInfo = getItem(position);

    if (convertView == null) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_currency, parent, false);


    }


      ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewCurrency);
      imageView.setImageResource(currencyInfo.getImage());


      TextView textView = convertView.findViewById(R.id.textViewAbbCurrency);
      textView.setText(currencyInfo.getCurrencyAbb());

      textView = convertView.findViewById(R.id.textViewNameCurrency);
      textView.setText(currencyInfo.getCurrencyName());


    return convertView;

  }
}
