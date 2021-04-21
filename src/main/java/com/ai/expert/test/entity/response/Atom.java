package com.ai.expert.test.entity.response;


import com.ai.expert.test.entity.GenericEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;


@Entity
@Table(name = "atom")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Atom extends Position implements GenericEntity {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String lemma;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idToken")
    @JsonBackReference
    private Token token;
}
