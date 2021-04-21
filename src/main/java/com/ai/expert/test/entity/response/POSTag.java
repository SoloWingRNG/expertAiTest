package com.ai.expert.test.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum POSTag {

    ADJ("adjective"),
    ADP("adposition"),
    ADV("adverb"),
    AUX("auxiliary"),
    CCONJ("coordinating conjunction"),
    DET("determiner"),
    INTJ("interjection"),
    NOUN("noun"),
    NUM("numeral"),
    PART("particle"),
    PRON("pronoun"),
    PROPN("proper noun"),
    PUNCT("punctuation"),
    SCONJ("subordinating conjunction"),
    SYM("symbol"),
    VERB("verb"),
    X("other");

    private final String description;

    private POSTag(String description) {
        this.description = description;
    }

    public static POSTag fromDescription(String description) {
        POSTag[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            POSTag b = var1[var3];
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
