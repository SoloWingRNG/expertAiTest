package com.ai.expert.test.entity.response;

import com.ai.expert.test.entity.GenericEntity;
import com.ai.expert.test.entity.ResponseDoc;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;



@Entity
@Table(name = "knowledge")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Knowledge implements GenericEntity {

        @Id
        @Column(name="id", nullable=false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Long syncon;
        private String label;

        @OneToMany(mappedBy = "knowledge", fetch = FetchType.LAZY)
        //@JsonManagedReference
        private List<Property> properties;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name="idResDoc")
        @JsonBackReference
        private DataModel responseDoc;

}
