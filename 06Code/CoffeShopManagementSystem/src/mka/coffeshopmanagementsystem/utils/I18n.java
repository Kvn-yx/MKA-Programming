/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class I18n {
    private static ResourceBundle bundle = ResourceBundle.getBundle("mka.coffeshopmanagementsystem.resources.messages", new Locale("en"));

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("mka.coffeshopmanagementsystem.resources.messages", locale);
    }

    public static String getString(String key) {
        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        }
        return "!" + key + "!";
    }
}
