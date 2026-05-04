/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña
 */
public class Table {
    private String id;
    private boolean state;
    private Employee assignedWaiter;

    public Table(String id) {
        this.id = id;
        this.state = false;
    }

    public void occupy() {
        this.state = true;
    }

    public void free() {
        this.state = false;
        this.assignedWaiter = null;
    }

    public boolean isOccupied() {
        return state;
    }

    public void assignWaiter(Employee waiter) {
        this.assignedWaiter = waiter;
    }
}