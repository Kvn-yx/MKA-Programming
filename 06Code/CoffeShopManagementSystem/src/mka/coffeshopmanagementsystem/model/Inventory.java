/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Inventory {
    private List<Ingredient> ingredients;
    private LocalDate lastReviewDate;

    public Inventory(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.lastReviewDate = LocalDate.now();
    }
    
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}

