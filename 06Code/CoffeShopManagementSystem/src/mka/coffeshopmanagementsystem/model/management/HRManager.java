/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.util.ArrayList;
import java.util.List;
import mka.coffeshopmanagementsystem.model.people.*;
import mka.coffeshopmanagementsystem.model.persistence.repository.IRepository;
import mka.coffeshopmanagementsystem.model.persistence.repository.JsonRepository;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class HRManager {
    private List<Employee> employees;
    private IRepository<Employee> employeeRepository;

    public HRManager() {
        this.employees = new ArrayList<>();
    }
    
    public HRManager(IRepository<Employee> employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.employees = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        if (employees == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(employees);
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    // Keep this method for backwards compatibility with the previous API if it was used in MainController
    public void setDataFilePath(String dataFilePath) {
        Type listType = new TypeToken<ArrayList<Employee>>(){}.getType();
        this.employeeRepository = new JsonRepository<>(dataFilePath, listType);
    }

    public void addEmployee(Employee employee) {
        if (employee != null) {
            List<Employee> currentEmployees = new ArrayList<>(getEmployees());
            currentEmployees.add(employee);
            this.employees = currentEmployees;
        }
    }

    public void removeEmployee(String id) {
        List<Employee> currentEmployees = new ArrayList<>(getEmployees());
        boolean removed = currentEmployees.removeIf(e -> e.getId().equals(id.trim()));
        if (removed) {
            this.employees = currentEmployees;
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("msg.invalid"));
        }
    }

    public void assignShift(String id, String shift) {
        Employee emp = getEmployees().stream().filter(e -> e.getId().equals(id.trim())).findFirst().orElse(null);
        if (emp != null) {
            emp.setShift(shift);
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("msg.invalid"));
        }
    }

    public void loadData() {
        if (employeeRepository != null) {
            this.employees = employeeRepository.findAll();
        } else {
            throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_hr"));
        }
    }

    public void saveData() {
        if (employeeRepository != null) {
            employeeRepository.saveAll(employees);
        } else {
             throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_hr"));
        }
    }
}
