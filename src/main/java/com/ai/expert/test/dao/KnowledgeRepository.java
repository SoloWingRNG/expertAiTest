package com.ai.expert.test.dao;


import com.ai.expert.test.entity.response.Knowledge;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeRepository extends GenericRepository<Knowledge, Long>{
}
