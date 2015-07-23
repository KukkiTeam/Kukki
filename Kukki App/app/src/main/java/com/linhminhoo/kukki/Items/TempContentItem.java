package com.linhminhoo.kukki.Items;

/**
 * Created by linhminhoo on 7/12/2015.
 */
public class TempContentItem {
    int id;
    String img_api_url, description;

    public TempContentItem(int id, String img_api_url, String description) {
        this.id = id;
        this.img_api_url = img_api_url;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_api_url() {
        return img_api_url;
    }

    public void setImg_api_url(String img_api_url) {
        this.img_api_url = img_api_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
