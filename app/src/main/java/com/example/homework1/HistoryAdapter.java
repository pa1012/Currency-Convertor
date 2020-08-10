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
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryConvert> {


  public HistoryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HistoryConvert> objects) {
    super(context, resource, objects);

  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    HistoryConvert item = getItem(position);

    if (convertView == null) {

      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_history, parent, false);

    }

    TextView textView = convertView.findViewById(R.id.textViewAmount);
    textView.setText(item.getAmountBC());

    return convertView;
  }
}
