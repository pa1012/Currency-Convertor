package com.example.homework1;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CurrencyInfo implements Serializable, Parcelable {
  String currencyAbb;
  String currencyName;
  double number;
  int image;
  boolean inTargetList;



  public CurrencyInfo(String s1, String s, double v, int img) {
    currencyAbb = s1;
    currencyName = s;
    number = v;
    image = img;
    inTargetList = false;

  }


  protected CurrencyInfo(Parcel in) {
    currencyAbb = in.readString();
    currencyName = in.readString();
    number = in.readDouble();
    image = in.readInt();
    inTargetList = in.readByte() != 0;
  }

  public static final Creator<CurrencyInfo> CREATOR = new Creator<CurrencyInfo>() {
    @Override
    public CurrencyInfo createFromParcel(Parcel in) {
      return new CurrencyInfo(in);
    }

    @Override
    public CurrencyInfo[] newArray(int size) {
      return new CurrencyInfo[size];
    }
  };

  public void setCurrencyAbb(String currencyAbb) {
    this.currencyAbb = currencyAbb;
  }

  public void setCurrencyName(String currencyName) {
    this.currencyName = currencyName;
  }

  public void setNumber(double number) {
    this.number = number;
  }

  public void setImage(int image) {
    this.image = image;
  }

  public void setInTargetList(boolean inTargetList) {
    this.inTargetList = inTargetList;
  }



  public String getCurrencyName() {
    return currencyName;
  }

  public String getCurrencyAbb() {
    return currencyAbb;
  }

  public double getNumber() {
    return number;
  }

  public int getImage() {
    return image;
  }

  public boolean isInTargetList(){
    return inTargetList;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(currencyAbb);
    parcel.writeString(currencyName);
    parcel.writeDouble(number);
    parcel.writeInt(image);
    parcel.writeByte((byte) (inTargetList ? 1 : 0));
  }
}
