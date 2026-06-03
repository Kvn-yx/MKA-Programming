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

    @Test
    public void testNormalizeGramsKeepsGrams() {
        // Test 5: "g", "gramo", "gramos" should remain "g"
        UnitConverter.ConversionResult resG = UnitConverter.normalize("g", new BigDecimal("100"));
        assertEquals("g", resG.unit);
        assertEquals(0, new BigDecimal("100").compareTo(resG.quantity));

        UnitConverter.ConversionResult resGramo = UnitConverter.normalize("gramo", new BigDecimal("200"));
        assertEquals("g", resGramo.unit);
        assertEquals(0, new BigDecimal("200").compareTo(resGramo.quantity));

        UnitConverter.ConversionResult resGramos = UnitConverter.normalize("gramos", new BigDecimal("300"));
        assertEquals("g", resGramos.unit);
        assertEquals(0, new BigDecimal("300").compareTo(resGramos.quantity));
    }

    @Test
    public void testNormalizePoundsToGrams() {
        // Test 6: 2 lb should normalize to 907.184 g (2 * 453.592)
        UnitConverter.ConversionResult res = UnitConverter.normalize("lb", new BigDecimal("2"));
        assertEquals("g", res.unit);
        assertEquals(0, new BigDecimal("907.184").compareTo(res.quantity));
    }

    @Test
    public void testNormalizeMillilitersKeepsMilliliters() {
        // Test 7: "ml", "mililitro" should remain "ml"
        UnitConverter.ConversionResult resMl = UnitConverter.normalize("ml", new BigDecimal("50"));
        assertEquals("ml", resMl.unit);
        assertEquals(0, new BigDecimal("50").compareTo(resMl.quantity));

        UnitConverter.ConversionResult resMililitro = UnitConverter.normalize("mililitro", new BigDecimal("150"));
        assertEquals("ml", resMililitro.unit);
        assertEquals(0, new BigDecimal("150").compareTo(resMililitro.quantity));
    }

    @Test
    public void testNormalizeOuncesToMilliliters() {
        // Test 8: 3 oz should normalize to 88.7205 ml (3 * 29.5735)
        UnitConverter.ConversionResult res = UnitConverter.normalize("oz", new BigDecimal("3"));
        assertEquals("ml", res.unit);
        assertEquals(0, new BigDecimal("88.7205").compareTo(res.quantity));
    }

    @Test
    public void testNormalizeTrimSpaces() {
        // Test 9: Spaces should be trimmed before normalization
        UnitConverter.ConversionResult resKg = UnitConverter.normalize(" kg ", new BigDecimal("1"));
        assertEquals("g", resKg.unit);
        assertEquals(0, new BigDecimal("1000").compareTo(resKg.quantity));

        UnitConverter.ConversionResult resMl = UnitConverter.normalize(" ml", new BigDecimal("500"));
        assertEquals("ml", resMl.unit);
        assertEquals(0, new BigDecimal("500").compareTo(resMl.quantity));
    }

    @Test
    public void testNormalizeZeroAndNegativeQuantities() {
        // Test 10: Zero and negative quantities should be handled correctly
        UnitConverter.ConversionResult resZero = UnitConverter.normalize("kg", BigDecimal.ZERO);
        assertEquals("g", resZero.unit);
        assertEquals(0, BigDecimal.ZERO.compareTo(resZero.quantity));

        UnitConverter.ConversionResult resNeg = UnitConverter.normalize("l", new BigDecimal("-1"));
        assertEquals("ml", resNeg.unit);
        assertEquals(0, new BigDecimal("-1000").compareTo(resNeg.quantity));
    }
}
