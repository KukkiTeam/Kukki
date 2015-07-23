package com.linhminhoo.kukki.Items;

/**
 * Created by linhminhoo on 7/15/2015.
 */
public class CommentItem {
    int id;
    String artist, description, created_at, url_api_avatar;

    public CommentItem(int id, String artist, String description, String created_at, String url_api_avatar) {
        this.id = id;
        this.artist = artist;
        this.description = description;
        this.created_at = created_at;
        this.url_api_avatar = url_api_avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUrl_api_avatar() {
        return url_api_avatar;
    }

    public void setUrl_api_avatar(String url_api_avatar) {
        this.url_api_avatar = url_api_avatar;
    }
}
