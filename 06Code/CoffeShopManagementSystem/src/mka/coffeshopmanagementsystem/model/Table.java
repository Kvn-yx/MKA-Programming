/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mka.coffeshopmanagementsystem.model;

/**
 *
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class Table {
    private String id;
    private boolean state;
    private Waiter assignedWaiter;

    public Table(String id) {
        this.id = id;
        this.state = false; // False = free
    }

    public void occupy() {
        this.state = true;
    }

    public void free() {
        this.state = false;
        this.assignedWaiter = null;
    }

    public void setAssignedWaiter(Waiter waiter) {
        this.assignedWaiter = waiter;
    }
    
    public String getId() {
        return id;
    }
}

