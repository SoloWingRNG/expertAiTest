package com.ai.expert.test.entity.response;

import com.ai.expert.test.entity.GenericEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "position")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy=InheritanceType.JOINED)
public class Position implements GenericEntity {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long start;
    private long end;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idCategory")
    @JsonBackReference
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idMainLemma")
    @JsonBackReference
    private MainLemma mainLemma;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idMainPhrase")
    @JsonBackReference
    private MainPhrase mainPhrase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idMainSyncon")
    @JsonBackReference
    private MainSyncon mainSyncon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idEntity")
    @JsonBackReference
    private com.ai.expert.test.entity.response.Entity entity;

}
