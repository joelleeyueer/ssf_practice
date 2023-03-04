package nus.iss.ssf.Models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;

public class CartItems {

    @NotNull(message = "itemname is null")
    @Pattern(regexp="^(apple|orange|bread|cheese|chicken|mineral_water|instant_noodles)$", message="item not found.")
    private String itemName;

    @NotNull(message = "qty is null")
    @Min(value=1, message="Qty min is 1")
    private Integer qty;

    public CartItems(String itemName, Integer qty) {
        this.itemName = itemName;
        this.qty = qty;
    }

    public CartItems() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    
    
    

    
    
}
