package com.ai.expert.test.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EntityType {

    ADR("Street address"),
    ANM("Animals"),
    BLD("Building"),
    COM("Businesses / companies"),
    DAT("Date"),
    DEV("Device"),
    DOC("RequestDocument"),
    EVN("Event"),
    FDD("Food/beverage"),
    GEA("Physical geographic features"),
    GEO("Administrative geographic areas"),
    GEX("Extended geography"),
    HOU("Hours"),
    LEN("Legal entities"),
    MAI("Email address"),
    MEA("Measure"),
    MMD("Mass media"),
    MON("Money"),
    NPH("Humans"),
    ORG("Organizations / societies / institutions"),
    PCT("Percentage"),
    PHO("Phone number"),
    PPH("Physical phenomena"),
    PRD("Product"),
    VCL("Vehicle"),
    WEB("Web address"),
    WRK("Work of human intelligence"),
    NPR("Proper noun");

    private final String description;

    private EntityType(String description) {
        this.description = description;
    }

    public static EntityType fromDescription(String description) {
        EntityType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            EntityType b = var1[var3];
            if (String.valueOf(b.description).equals(description)) {
                return b;
            }
        }
        return null;
    }

    public String getDescription() {
        return this.description;
    }



}
