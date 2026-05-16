/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.management;

import java.util.List;
import mka.coffeshopmanagementsystem.model.floor.Table;
import mka.coffeshopmanagementsystem.model.floor.Machine;
import mka.coffeshopmanagementsystem.model.people.Waiter;
import mka.coffeshopmanagementsystem.model.persistence.repository.ISingleRepository;
import mka.coffeshopmanagementsystem.model.persistence.repository.JsonSingleRepository;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class FloorManager {
    private List<Table> tables;
    private List<Machine> machines;
    private transient ISingleRepository<FloorManager> floorRepository;

    public FloorManager() {
    }
    
    public FloorManager(ISingleRepository<FloorManager> floorRepository) {
        this.floorRepository = floorRepository;
    }

    public List<Table> getTables() {
        if (tables == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(tables);
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Machine> getMachines() {
        if (machines == null) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(machines);
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void setDataFilePath(String dataFilePath) {
        this.floorRepository = new JsonSingleRepository<>(dataFilePath, FloorManager.class);
    }

    public void addTable(Table table) {
        if (table != null) {
            List<Table> currentTables = new java.util.ArrayList<>(getTables());
            currentTables.add(table);
            this.tables = currentTables;
        }
    }

    public void removeTable(String id) {
        List<Table> currentTables = new java.util.ArrayList<>(getTables());
        boolean removed = currentTables.removeIf(t -> t.getId().equals(id.trim()));
        if (removed) {
            this.tables = currentTables;
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("flr.notFound"));
        }
    }

    public void addMachine(Machine machine) {
        if (machine != null) {
            List<Machine> currentMachines = new java.util.ArrayList<>(getMachines());
            currentMachines.add(machine);
            this.machines = currentMachines;
        }
    }

    public void removeMachine(String id) {
        List<Machine> currentMachines = new java.util.ArrayList<>(getMachines());
        boolean removed = currentMachines.removeIf(m -> m.getId() != null && m.getId().equals(id.trim()));
        if (removed) {
            this.machines = currentMachines;
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("flr.notFound"));
        }
    }

    public void assignTable(String tableId, Waiter waiter) {
        Table table = getTables().stream().filter(t -> t.getId().equals(tableId.trim())).findFirst().orElse(null);
        if (table != null && waiter != null) {
            table.setAssignedWaiter(waiter);
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("flr.notFound"));
        }
    }

    public void toggleTableState(String id) {
        Table table = getTables().stream().filter(t -> t.getId().equals(id.trim())).findFirst().orElse(null);
        if (table != null) {
            if (table.isState()) table.free();
            else table.occupy();
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("flr.notFound"));
        }
    }

    public void toggleMachineState(String id) {
        Machine machine = getMachines().stream().filter(m -> m.getId() != null && m.getId().equals(id.trim())).findFirst().orElse(null);
        if (machine != null) {
            if (machine.isState()) machine.turnOff();
            else machine.turnOn();
        } else {
            throw new IllegalArgumentException(mka.coffeshopmanagementsystem.utils.I18n.getString("flr.notFound"));
        }
    }

    public void loadData() {
        if (floorRepository != null) {
            FloorManager loadedData = floorRepository.load();
            if (loadedData != null) {
                this.tables = loadedData.getTables();
                this.machines = loadedData.getMachines();
            }
        } else {
             throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_floor"));
        }
        
        if (this.tables == null) {
            this.tables = new java.util.ArrayList<>();
        }
        if (this.machines == null) {
            this.machines = new java.util.ArrayList<>();
        }
    }

    public void saveData() {
        if (floorRepository != null) {
            floorRepository.save(this);
        } else {
             throw new IllegalStateException(mka.coffeshopmanagementsystem.utils.I18n.getString("model.repo.err_floor"));
        }
    }
}
