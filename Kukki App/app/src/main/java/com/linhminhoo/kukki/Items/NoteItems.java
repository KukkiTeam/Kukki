package com.linhminhoo.kukki.Items;

/**
 * Created by linhminhoo on 7/21/2015.
 */
public class NoteItems {
    int id, user_id;
    String title;

    public NoteItems(int id, int user_id, String title) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
