package com.example.homework1;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.homework1.NetworkUtils;

public class RateLoader extends AsyncTaskLoader<Bundle> {
  private String mQueryString;
  int ID;

  public RateLoader(@NonNull Context context, String queryString, int id) {
    super(context);
    mQueryString = queryString;
    ID = id;
  }

  @Override
  protected void onStartLoading() {

    //call forceLoad() to start the loadInBackground() method.
    //The loader will not start loading data until you call the forceLoad() method.

    super.onStartLoading();
    forceLoad();
  }


  @Nullable
  @Override
  public Bundle loadInBackground() {
    return NetworkUtils.getInfo(mQueryString,ID);
  }
}
