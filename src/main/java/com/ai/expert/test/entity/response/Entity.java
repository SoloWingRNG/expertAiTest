package com.ai.expert.test.entity.response;

import com.ai.expert.test.entity.GenericEntity;
import com.ai.expert.test.entity.ResponseDoc;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@javax.persistence.Entity
@Table(name = "entity")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Entity implements GenericEntity {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long syncon;
    //private EntityType type;
    private String type;
    private String lemma;

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Position> positions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idResDoc")
    private DataModel responseDoc;
}
