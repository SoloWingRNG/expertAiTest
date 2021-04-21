package com.ai.expert.test.entity.response;

import com.ai.expert.test.entity.GenericEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "property")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Property implements GenericEntity {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idKnow")
   // @JsonBackReference
    private Knowledge knowledge;


}
