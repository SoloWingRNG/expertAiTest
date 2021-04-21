package com.ai.expert.test.dao;

import com.ai.expert.test.entity.response.Sentence;
import org.springframework.stereotype.Repository;

@Repository
public interface SentencesRepository extends GenericRepository<Sentence,Long>{
}
