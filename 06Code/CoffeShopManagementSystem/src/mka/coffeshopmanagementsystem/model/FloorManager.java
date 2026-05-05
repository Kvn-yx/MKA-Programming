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
public class FloorManager {
    private List<Table> tables;
    private List<Machine> machines;

    public FloorManager(List<Table> tables, List<Machine> machines) {
        this.tables = tables;
        this.machines = machines;
    }

    public void assignTable(Waiter waiter, Table table) {
        table.setAssignedWaiter(waiter);
    }
}

