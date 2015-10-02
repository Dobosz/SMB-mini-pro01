package pl.dobosz.smb01.app.models;

import java.io.Serializable;

/**
 * Created by dobosz on 9/29/15.
 */
public class CartItem implements Serializable{

    private long id;
    private String name;
    private String description;
    private int quantity;

    public CartItem() {
    }

    public CartItem(String name, String description, int quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
