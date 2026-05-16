package mka.coffeshopmanagementsystem.utils;

import java.math.BigDecimal;

/**
 * Utility class to standardize inventory units.
 */
public class UnitConverter {

    public static class ConversionResult {
        public final String unit;
        public final BigDecimal quantity;

        public ConversionResult(String unit, BigDecimal quantity) {
            this.unit = unit;
            this.quantity = quantity;
        }
    }

    public static ConversionResult normalize(String unit, BigDecimal quantity) {
        if (unit == null || unit.trim().isEmpty()) {
            return new ConversionResult("u", quantity);
        }
        
        String u = unit.trim().toLowerCase();
        
        // Weight
        if (u.equals("kg") || u.equals("kilo") || u.equals("kilogramo") || u.equals("kilogramos")) {
            return new ConversionResult("g", quantity.multiply(new BigDecimal("1000")));
        } else if (u.equals("g") || u.equals("gramo") || u.equals("gramos") || u.equals("gr")) {
            return new ConversionResult("g", quantity);
        } else if (u.equals("lb") || u.equals("libra") || u.equals("libras")) {
            return new ConversionResult("g", quantity.multiply(new BigDecimal("453.592")));
        }
        
        // Volume
        else if (u.equals("l") || u.equals("litro") || u.equals("litros")) {
            return new ConversionResult("ml", quantity.multiply(new BigDecimal("1000")));
        } else if (u.equals("ml") || u.equals("mililitro") || u.equals("mililitros")) {
            return new ConversionResult("ml", quantity);
        } else if (u.equals("oz") || u.equals("onza") || u.equals("onzas")) {
            return new ConversionResult("ml", quantity.multiply(new BigDecimal("29.5735")));
        }
        
        // Default to Units
        return new ConversionResult("u", quantity);
    }
}
