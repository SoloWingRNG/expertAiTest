package com.ai.expert.test.service;

import com.ai.expert.test.entity.FileEntity;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface FileService {

    DataTablesOutput<FileEntity> findAll(DataTablesInput input);
    void storeFileDb();
    void deleteFileEntityById(Long id) throws Exception;
    void processFile(Long id) throws Exception;

}
