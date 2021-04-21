package com.ai.expert.test.entity.response;

import com.ai.expert.test.entity.GenericEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@javax.persistence.Entity
@Table(name = "response_document")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataModel implements GenericEntity {


    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content",columnDefinition = "VARCHAR(10000) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String content;

    @Column(name = "language")
    private String language;

    @Column(name = "version")
    private String version;

    @Column(name = "fileName")
    private String fileName;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Knowledge> knowledge;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Token> tokens;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Phrase> phrases;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Sentence> sentences;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Paragraph> paragraphs;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Topic> topics;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MainSentence> mainSentences;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MainPhrase> mainPhrases;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MainLemma> mainLemmas;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MainSyncon> mainSyncons;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<com.ai.expert.test.entity.response.Entity> entities;

    @OneToMany(mappedBy = "responseDoc", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Category> categories;
}
