package com.linhminhoo.kukki.Items;

/**
 * Created by linhminhoo on 7/11/2015.
 */
public class TempMaterialItem {
    int id;
    String name;

    public TempMaterialItem(int id, String name) {
        this.id = id;
        this.name = name;
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
}
