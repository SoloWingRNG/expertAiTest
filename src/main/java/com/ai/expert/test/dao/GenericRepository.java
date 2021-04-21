package com.ai.expert.test.dao;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends DataTablesRepository<T, ID>{

}
