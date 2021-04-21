package com.ai.expert.test.entity.response;

import com.ai.expert.test.entity.GenericEntity;
import com.ai.expert.test.entity.ResponseDoc;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "phrase")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Phrase extends Position implements GenericEntity {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "tokens_table", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "tokens")
    private List<Long> tokens;

    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idResDoc")
    @JsonBackReference
    private DataModel responseDoc;
}
