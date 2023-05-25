package pincer.tests;

import hu.unideb.inf.swe.pincer.util.DiscountCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DiscountTest {

    @Test
    void testMaxDiscount() {

        Integer subtotal = 36501;

        Integer result = DiscountCalculator.calculate(subtotal);

        assertEquals(29201, result);
    }

    @Test
    void testMidDiscount() {

        Integer subtotal = 19999;

        Integer result = DiscountCalculator.calculate(subtotal);

        assertEquals(17999, result);
    }

    @Test
    void testMinDiscount() {

        Integer subtotal = 5001;

        Integer result = DiscountCalculator.calculate(subtotal);

        assertEquals(4751, result);
    }

    @Test
    void testNoDiscount() {

        Integer subtotal = 625;

        Integer result = DiscountCalculator.calculate(subtotal);

        assertEquals(625, result);
    }

    @Test
    void testWrongSubtotal() {

        Integer subtotal = -23500;

        assertThrows(ArithmeticException.class, () -> DiscountCalculator.calculate(subtotal));
    }
}
