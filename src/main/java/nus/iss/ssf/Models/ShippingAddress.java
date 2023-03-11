package nus.iss.ssf.Models;

import jakarta.validation.constraints.NotBlank;

public class ShippingAddress {

    @NotBlank(message="Please type a name or alias.")
    private String name;

    @NotBlank(message="Please type an address")
    private String address;

    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }

    public String getaddress() {
        return address;
    }
    public void setaddress(String address) {
        this.address = address;
    }
    
}
