package com.ai.expert.test.dao;

import com.ai.expert.test.entity.response.Phrase;
import org.springframework.stereotype.Repository;

@Repository
public interface PhrasesRepository extends GenericRepository<Phrase, Long>{
}
