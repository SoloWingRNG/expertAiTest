package com.ai.expert.test.entity.response;

import com.ai.expert.test.entity.GenericEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "dependency")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Dependency implements GenericEntity {


    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long idDepency;
    private Long head;
    private String label;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idTok")
    private Token token;


}
