package com.ai.expert.test.entity.response;

import com.ai.expert.test.entity.GenericEntity;
import com.ai.expert.test.entity.ResponseDoc;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Token extends Position implements  GenericEntity{

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long syncon;
    private String type;
    //private TokenType type;
    /*private POSTag pos;*/
    private String pos;
    private String lemma;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "token",fetch = FetchType.EAGER)
    private Dependency dependency;

    private String morphology;
    private Long paragraph;
    private Long sentence;
    private Long phrase;

    @OneToMany(mappedBy = "token", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Atom> atoms;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "token",fetch = FetchType.EAGER)
    private Vsyncon vsyn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idResDoc")
    @JsonBackReference
    private DataModel responseDoc;


}
