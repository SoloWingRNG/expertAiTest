package com.ai.expert.test.dao;


import com.ai.expert.test.entity.response.Paragraph;
import org.springframework.stereotype.Repository;

@Repository
public interface ParagraphsRepository extends GenericRepository<Paragraph,Long>{
}
