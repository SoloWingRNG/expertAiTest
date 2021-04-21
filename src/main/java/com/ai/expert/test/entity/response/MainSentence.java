package com.ai.expert.test.entity.response;


import com.ai.expert.test.entity.GenericEntity;
import com.ai.expert.test.entity.ResponseDoc;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;


@Entity
@Table(name = "main_sentence")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MainSentence extends Position implements GenericEntity {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "value",columnDefinition = "TEXT")
    private String value;

    private Float score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idResDoc")
    private DataModel responseDoc;
}
