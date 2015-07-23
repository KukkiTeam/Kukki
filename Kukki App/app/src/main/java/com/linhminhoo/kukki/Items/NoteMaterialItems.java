package com.linhminhoo.kukki.Items;

/**
 * Created by linhminhoo on 7/21/2015.
 */
public class NoteMaterialItems {
    int id, isChecked, note_id;
    String name;

    public NoteMaterialItems(int id, int isChecked, int note_id, String name) {
        this.id = id;
        this.isChecked = isChecked;
        this.note_id = note_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
