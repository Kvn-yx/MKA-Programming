/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.util.List;
import mka.coffeshopmanagementsystem.model.floor.Table;
import mka.coffeshopmanagementsystem.model.floor.Machine;
import mka.coffeshopmanagementsystem.model.people.Waiter;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class FloorManager {
    private List<Table> tables;
    private List<Machine> machines;
    private String dataFilePath;

    public FloorManager() {
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public void assignTable(Waiter waiter, Table table) {
        if (table != null && waiter != null) {
            table.setAssignedWaiter(waiter);
            System.out.println("Mesero " + waiter.getName() + " asignado exitosamente a la mesa " + table.getId());
        }
    }

    public void loadData() {
        mka.coffeshopmanagementsystem.model.persistence.JsonFileManager jfm = new mka.coffeshopmanagementsystem.model.persistence.JsonFileManager();
        FloorManager loadedData = jfm.loadFromFile(dataFilePath, FloorManager.class);
        
        if (loadedData != null) {
            this.tables = loadedData.getTables();
            this.machines = loadedData.getMachines();
            System.out.println("Datos de piso cargados exitosamente.");
        }
        
        // Inicialización segura
        if (this.tables == null) {
            this.tables = new java.util.ArrayList<>();
        }
        if (this.machines == null) {
            this.machines = new java.util.ArrayList<>();
        }
    }

    public void saveData() {
        mka.coffeshopmanagementsystem.model.persistence.JsonFileManager jfm = new mka.coffeshopmanagementsystem.model.persistence.JsonFileManager();
        jfm.saveToFile(dataFilePath, this);
    }
}
