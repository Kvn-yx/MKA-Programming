/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.util.List;
import mka.coffeshopmanagementsystem.model.people.Employee;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class HRManager {
    private List<Employee> employees;
    private String dataFilePath;

    public HRManager() {
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public void assignShift(Employee employee) {
        // TODO: implement
    }

    public void loadData() {
        // TODO: implement
    }

    public void saveData() {
        // TODO: implement
    }
}
