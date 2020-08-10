package com.example.homework1;

public class CurrencyConverter {
  private double Amount = 0;
  private String Currency= "";


  public CurrencyConverter(double amount, String currency) {
    Amount = amount;
    Currency = currency;

  }

  public double execute()
  {
    return Amount * 0.1;
  }
}
