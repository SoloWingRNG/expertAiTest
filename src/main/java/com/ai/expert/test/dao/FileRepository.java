package com.ai.expert.test.dao;

import com.ai.expert.test.entity.FileEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends GenericRepository<FileEntity, Long>{

    void deleteFileEntityById(Long id);

}
