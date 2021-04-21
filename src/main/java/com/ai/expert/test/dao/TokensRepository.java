package com.ai.expert.test.dao;

import com.ai.expert.test.entity.response.Token;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRepository extends GenericRepository<Token,Long>{
}
