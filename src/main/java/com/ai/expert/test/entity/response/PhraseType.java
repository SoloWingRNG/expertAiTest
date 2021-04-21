package com.ai.expert.test.entity.response;


public enum PhraseType {

    AP("Adjective Phrase"),
    CP("Conjunction Phrase"),
    CR("Blank lines"),
    DP("Adverb Phrase"),
    NP("Noun Phrase"),
    PN("Nominal Predicate "),
    PP("Preposition Phrase"),
    RP("Relative Phrase"),
    VP("Verb Phrase"),
    NA("Not Applicable");


    private final String description;

    private PhraseType(String description) {
        this.description = description;
    }

    public static PhraseType fromDescription(String description) {
        PhraseType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            PhraseType b = var1[var3];
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
