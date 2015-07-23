package com.linhminhoo.kukki.Items;

/**
 * Created by linhminhoo on 7/12/2015.
 */
public class AreaContinentItem {
    String area, continent;

    public AreaContinentItem(String area, String continent) {
        this.area = area;
        this.continent = continent;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
