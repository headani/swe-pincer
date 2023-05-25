package hu.unideb.inf.swe.pincer.util;

public class DiscountCalculator {

    private DiscountCalculator() {
    }

    public static Integer calculate(Integer subtotal) {
        if (subtotal > 20000) {
            return Math.toIntExact(Math.round(subtotal * 0.80));
        } else if (subtotal > 10000) {
            return Math.toIntExact(Math.round(subtotal * 0.90));
        } else if (subtotal > 5000) {
            return Math.toIntExact(Math.round(subtotal * 0.95));
        } else if (subtotal < 0) {
            throw new ArithmeticException("Subtotal below zero!");
        } else {
            return subtotal;
        }
    }
}
