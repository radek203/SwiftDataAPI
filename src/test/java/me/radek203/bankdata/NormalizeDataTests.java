package me.radek203.bankdata;

import me.radek203.bankdata.entities.Bank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NormalizeDataTests {

    @Test
    void normalizeData_trimsAndConvertsToUpperCase() {
        String result = Bank.normalizeData("  example  ");
        Assertions.assertEquals("EXAMPLE", result);
    }

    @Test
    void normalizeData_emptyStringReturnsNull() {
        String result = Bank.normalizeData("  ");
        Assertions.assertNull(result);
    }

    @Test
    void normalizeData_nullInputReturnsNull() {
        String result = Bank.normalizeData(null);
        Assertions.assertNull(result);
    }

    @Test
    void normalizeData_alreadyTrimmedAndUpperCase() {
        String result = Bank.normalizeData("EXAMPLE");
        Assertions.assertEquals("EXAMPLE", result);
    }

    @Test
    void normalizeData_mixedCaseInput() {
        String result = Bank.normalizeData("eXaMpLe");
        Assertions.assertEquals("EXAMPLE", result);
    }

}
