package com.linhminhoo.kukki.Items;

/**
 * Created by linhminhoo on 6/25/2015.
 */
public class UserItems {
    int id, total_receipt, total_followed;
    String name, email, password, api_key;

    public UserItems(int id, String name, String email, String password, String api_key, int total_receipt, int total_followed) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.api_key = api_key;
        this.total_receipt=total_receipt;
        this.total_followed=total_followed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public int getTotal_receipt() {
        return total_receipt;
    }

    public void setTotal_receipt(int total_receipt) {
        this.total_receipt = total_receipt;
    }

    public int getTotal_followed() {
        return total_followed;
    }

    public void setTotal_followed(int total_followed) {
        this.total_followed = total_followed;
    }
}
