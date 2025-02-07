package com.example.webservicepostbackbuilder.services;

public class BaseConverter {

    private static final String DECIMAL_DIGITS = "0123456789";
    private final String digits;
    private final char sign;

    public BaseConverter() {
        this.digits = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
        this.sign = '-';
    }

    private String convert(String number, String fromDigits, String toDigits) {
        int x = 0;
        for (char digit : number.toCharArray()) {
            int index = fromDigits.indexOf(digit);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid digit: " + digit);
            }
            x = x * fromDigits.length() + index;
        }

        if (x == 0) {
            return String.valueOf(toDigits.charAt(0));
        }

        StringBuilder result = new StringBuilder();
        while (x > 0) {
            int digit = x % toDigits.length();
            result.insert(0, toDigits.charAt(digit));
            x /= toDigits.length();
        }
        return result.toString();
    }

    public String encode(int number) {
        boolean neg = number < 0;
        String numStr = String.valueOf(Math.abs(number));
        String value = convert(numStr, DECIMAL_DIGITS, digits);
        return neg ? sign + value : value;
    }

    public int decode(String number) {
        boolean neg = number.charAt(0) == sign;
        if (neg) {
            number = number.substring(1);
        }
        int value = Integer.parseInt(convert(number, digits, DECIMAL_DIGITS));
        return neg ? -value : value;
    }

}
