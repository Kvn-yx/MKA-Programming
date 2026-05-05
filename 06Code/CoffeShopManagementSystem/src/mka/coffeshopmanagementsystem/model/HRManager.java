/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

import java.util.List;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class HRManager {
    private List<Employee> employees;

    public HRManager(List<Employee> employees) {
        this.employees = employees;
    }

    public void assignShift(Employee employee) {
        System.out.println(employee.getName() + " assigned to shift.");
    }
}

