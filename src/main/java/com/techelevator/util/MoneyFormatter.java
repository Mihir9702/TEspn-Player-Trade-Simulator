package com.techelevator.util;

import java.text.NumberFormat;

public class MoneyFormatter {

  /**
   * Format money according to local currency standards e.g. $1.00 for USD.
   *
   * @param amount The amount of money.
   * @return The String representation for the amount of money.
   */
  public static String formatMoney(double amount) {
    return NumberFormat.getCurrencyInstance().format(amount);
  }
}
