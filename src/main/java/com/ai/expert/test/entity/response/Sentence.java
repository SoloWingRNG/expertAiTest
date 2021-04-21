package com.ai.expert.test.entity.response;

import com.ai.expert.test.entity.GenericEntity;
import com.ai.expert.test.entity.ResponseDoc;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "sentence")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sentence extends Position implements GenericEntity {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "phrases_table", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "phrases")
    private List<Long> phrases;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idResDoc")
    @JsonBackReference
    private DataModel responseDoc;


}
