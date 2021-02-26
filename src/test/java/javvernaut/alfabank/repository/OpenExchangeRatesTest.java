package javvernaut.alfabank.repository;

import javvernaut.alfabank.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static javvernaut.alfabank.TestData.CURRENCY_CODE;
import static javvernaut.alfabank.TestData.WRONG_CURRENCY_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenExchangeRatesTest extends BaseTest {

    @Autowired
    OpenExchangeRates openExchangeRates;

    @Test
    void getShift() {
        assertEquals(openExchangeRates.getShift(CURRENCY_CODE), -1);
    }

    @Test
    void getShiftWrongCurrencyCode() {
        assertEquals(openExchangeRates.getShift(WRONG_CURRENCY_CODE), 0);
    }
}