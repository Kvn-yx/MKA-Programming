/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model.floor;

import mka.coffeshopmanagementsystem.model.people.Waiter;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Table {
    private String id;
    private boolean state;
    private Waiter assignedWaiter;

    public Table() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Waiter getAssignedWaiter() {
        return assignedWaiter;
    }

    public void setAssignedWaiter(Waiter assignedWaiter) {
        this.assignedWaiter = assignedWaiter;
    }

    public void occupy() {
        this.state = true;
    }

    public void free() {
        this.state = false;
        this.assignedWaiter = null;
    }
}
