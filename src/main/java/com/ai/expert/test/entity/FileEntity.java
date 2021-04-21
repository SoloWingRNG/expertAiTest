
package com.ai.expert.test.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="file_folder")
@Accessors(chain=true)
public class FileEntity implements GenericEntity {


    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name="title")
    @Getter@Setter
    private String title;

    @Column(name="size")
    @Getter@Setter
    private Long size;

    @Lob
    @Column(name="file")
    @Getter@Setter
    private byte[] file;

}

