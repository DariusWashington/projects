package com.lendingcatalog.model;

import com.lendingcatalog.util.Logger;

import java.util.UUID;

public class Tool implements CatalogItem {

    private String id;
    private String type;
    private String manufacturer;
    private int count;



    public Tool(String type, String manufacturer, int count) {

        this.type = type;
        this.manufacturer = manufacturer;
        this.count = count;
    }

    @Override
    public boolean matchesName(String name) {
        return type.toLowerCase().contains(name.toLowerCase());
    }

    @Override
    public boolean matchesCreator(String creator) {
        return manufacturer.toLowerCase().contains(creator.toLowerCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return false;
    }

    @Override
    public void registerItem() {
       id = UUID.randomUUID().toString();
       Logger.log("Tool created: " + this.toString(), "tool.log");
    }

    @Override
    public String toString() {
        return "* " + type + System.lineSeparator()
                + " - Manufactured by: " + manufacturer + System.lineSeparator()
                + " - Id: " + id + System.lineSeparator()
                + "- Quantity: " + count;
    }
}
