package mka.coffeshopmanagementsystem;

import static org.junit.Assert.*;
import org.junit.Test;
import java.math.BigDecimal;
import mka.coffeshopmanagementsystem.utils.UnitConverter;

/**
 * Unit tests for UnitConverter class.
 * Validates normalizations of weight and volume units.
 * Contains 4 base test cases implemented.
 * 
 * NOTE TO THE DEVELOPMENT TEAM: 
 * Please implement the remaining 6 test cases to complete the 10 tests requirement.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class UnitConverterTest {

    // === IMPLEMENTED BASE TESTS (4 cases) ===

    @Test
    public void testNormalizeKilogramsToGrams() {
        // Test 1: 1.5 kg should normalize to 1500 g
        UnitConverter.ConversionResult res = UnitConverter.normalize("kg", new BigDecimal("1.5"));
        assertEquals("g", res.unit);
        assertEquals(0, new BigDecimal("1500").compareTo(res.quantity));
    }

    @Test
    public void testNormalizeLitersToMilliliters() {
        // Test 2: 0.5 liters should normalize to 500 ml
        UnitConverter.ConversionResult res = UnitConverter.normalize("l", new BigDecimal("0.5"));
        assertEquals("ml", res.unit);
        assertEquals(0, new BigDecimal("500").compareTo(res.quantity));
    }

    @Test
    public void testNormalizeUnknownUnitReturnsDefault() {
        // Test 3: Unknown units like "box" should fall back to default "u" (units) without changing the quantity
        UnitConverter.ConversionResult res = UnitConverter.normalize("box", new BigDecimal("7"));
        assertEquals("u", res.unit);
        assertEquals(0, new BigDecimal("7").compareTo(res.quantity));
    }

    @Test
    public void testNormalizeCaseInsensitivity() {
        // Test 4: "KG" (uppercase) should be processed exactly like "kg"
        UnitConverter.ConversionResult res = UnitConverter.normalize("KG", new BigDecimal("2.5"));
        assertEquals("g", res.unit);
        assertEquals(0, new BigDecimal("2500").compareTo(res.quantity));
    }

    // === PLACEHOLDERS FOR TEAM MEMBERS (6 cases remaining) ===

    // TODO: Member 1 - Implement Test 5: testNormalizeGramsKeepsGrams
    // public void testNormalizeGramsKeepsGrams() { ... }

    // TODO: Member 2 - Implement Test 6: testNormalizePoundsToGrams (1 lb = 453.592 g)
    // public void testNormalizePoundsToGrams() { ... }

    // TODO: Member 3 - Implement Test 7: testNormalizeMillilitersKeepsMilliliters
    // public void testNormalizeMillilitersKeepsMilliliters() { ... }

    // TODO: Member 4 - Implement Test 8: testNormalizeOuncesToMilliliters (1 oz = 29.5735 ml)
    // public void testNormalizeOuncesToMilliliters() { ... }

    // TODO: Member 5 - Implement Test 9: testNormalizeTrimSpaces (e.g. " kg " -> "g")
    // public void testNormalizeTrimSpaces() { ... }

    // TODO: Member 6 - Implement Test 10: testNormalizeZeroAndNegativeQuantities
    // public void testNormalizeZeroAndNegativeQuantities() { ... }
}
