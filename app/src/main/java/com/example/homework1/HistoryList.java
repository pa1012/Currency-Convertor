package com.example.homework1;


import java.io.Serializable;
import java.util.ArrayList;

public class HistoryList implements Serializable {


  public ArrayList<HistoryConvert> list;


  public void add(HistoryConvert a){
    list.add(a);
  }

}
