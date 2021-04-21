package com.ai.expert.test.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TokenType {

    ADJ("Adjective"),
    ADV("Adverb"),
    ART("Article"),
    AUX("Auxiliary verb"),
    CON("Conjunction"),
    NOU("Noun"),
    NOU_ADR("Street address"),
    NOU_DAT("Date"),
    NOU_HOU("Hour"),
    NOU_MAI("Email address"),
    NOU_MEA("Measure"),
    NOU_MON("Money"),
    NOU_PCT("Percentage"),
    NOU_PHO("Phone number"),
    NOU_WEB("Web address"),
    NPR("Proper noun"),
    NPR_ANM("Proper noun of an animal"),
    NPR_BLD("Proper noun of a building"),
    NPR_COM("Proper noun of a business/company"),
    NPR_DEV("Proper noun of a device"),
    NPR_DOC("Proper noun of a document"),
    NPR_EVN("Proper noun of an event"),
    NPR_FDD("Proper noun of a food/beverage"),
    NPR_GEA("Proper noun of a physical geographic feature"),
    NPR_GEO("Proper noun of an administrative geographic area"),
    NPR_GEX("Proper noun of an extra-terrestrial or imaginary place"),
    NPR_LEN("Proper noun of a legal/fiscal entity"),
    NPR_MMD("Proper noun of a mass media"),
    NPR_NPH("Proper noun of a human being"),
    NPR_ORG("Proper noun of an organization/society/institution"),
    NPR_PPH("Proper noun of a physical phenomena"),
    NPR_PRD("Proper noun of a product"),
    NPR_VCL("Proper noun of a vehicle"),
    NPR_WRK("Proper noun of a work of human intelligence"),
    PNT("Punctuation mark"),
    PRE("Preposition"),
    PRO("Pronoun"),
    PRT("Particle"),
    VER("Verb"),
    ANY("Any entity type");

    private final String description;

    private TokenType(String description) {
        this.description = description;
    }

    public static TokenType fromDescription(String description) {
        TokenType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            TokenType b = var1[var3];
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
