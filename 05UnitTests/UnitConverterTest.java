package mka.coffeshopmanagementsystem;

import static org.junit.Assert.*;
import org.junit.Test;
import java.math.BigDecimal;
import mka.coffeshopmanagementsystem.utils.UnitConverter;

/**
 * Unit tests for UnitConverter class.
 * Validates normalizations of grams, milliliters, pounds, ounces, kilograms, liters.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class UnitConverterTest {

    @Test
    public void testWeightNormalization() {
        // 1 kg = 1000 g
        assertEquals(0, new BigDecimal("1000").compareTo(UnitConverter.normalize("kg", new BigDecimal("1")).quantity));
        assertEquals("g", UnitConverter.normalize("kg", new BigDecimal("1")).unit);

        // 500 g = 500 g
        assertEquals(0, new BigDecimal("500").compareTo(UnitConverter.normalize("g", new BigDecimal("500")).quantity));
        assertEquals("g", UnitConverter.normalize("g", new BigDecimal("500")).unit);

        // 1 lb = 453.592 g
        assertEquals(0, new BigDecimal("453.592").compareTo(UnitConverter.normalize("lb", new BigDecimal("1")).quantity));
    }

    @Test
    public void testVolumeNormalization() {
        // 1 L = 1000 ml
        assertEquals(0, new BigDecimal("1000").compareTo(UnitConverter.normalize("l", new BigDecimal("1")).quantity));
        assertEquals("ml", UnitConverter.normalize("l", new BigDecimal("1")).unit);

        // 250 ml = 250 ml
        assertEquals(0, new BigDecimal("250").compareTo(UnitConverter.normalize("ml", new BigDecimal("250")).quantity));
        assertEquals("ml", UnitConverter.normalize("ml", new BigDecimal("250")).unit);

        // 1 oz = 29.5735 ml
        assertEquals(0, new BigDecimal("29.5735").compareTo(UnitConverter.normalize("oz", new BigDecimal("1")).quantity));
    }

    @Test
    public void testUnknownUnit() {
        assertEquals("u", UnitConverter.normalize("box", new BigDecimal("5")).unit);
        assertEquals(0, new BigDecimal("5").compareTo(UnitConverter.normalize("box", new BigDecimal("5")).quantity));
    }
}
