package com.example.homework1;

import android.text.NoCopySpan;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class HistoryConvert implements Serializable {
  private String amountBC;

  private ArrayList<CurrencyInfo> targetCurrencyList;

  //private File file;

  public String getAmountBC() {
    return amountBC;
  }

  public void setAmountBC(String amountBC) {
    this.amountBC = amountBC;
  }


  public ArrayList<CurrencyInfo> getTargetCurrencyList() {
    return targetCurrencyList;
  }

  public void setTargetCurrencyList(ArrayList<CurrencyInfo> targetCurrencyList) {
    this.targetCurrencyList = targetCurrencyList;
  }


}
