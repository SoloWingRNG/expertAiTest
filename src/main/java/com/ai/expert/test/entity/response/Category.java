package com.ai.expert.test.entity.response;


import com.ai.expert.test.entity.GenericEntity;
import com.ai.expert.test.entity.ResponseDoc;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category implements GenericEntity {

    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namespace;
    private String label;

    @ElementCollection
    @CollectionTable(name = "hierarchy_table", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "hierarchy")
    private List<String> hierarchy;

    private Float frequency;
    private Float score;
    private Boolean winner;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Position> positions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idResDoc")
    @JsonBackReference
    private DataModel responseDoc;


}
